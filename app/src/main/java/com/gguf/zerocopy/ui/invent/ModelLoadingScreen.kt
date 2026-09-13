package com.gguf.zerocopy.ui.invent

import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Cpu
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gguf.zerocopy.ZeroCopyApp
import com.gguf.zerocopy.data.local.ModelLoadEvent
import com.gguf.zerocopy.data.local.ModelLoadMonitor
import com.gguf.zerocopy.data.repository.LocalModel
import com.gguf.zerocopy.domain.device.DeviceInfo
import com.gguf.zerocopy.domain.inference.InferenceConfig
import com.gguf.zerocopy.domain.inference.InferenceEngine
import com.gguf.zerocopy.ui.components.GradientSearchingCircle
import com.gguf.zerocopy.ui.theme.ZcPalette
import com.gguf.zerocopy.ui.theme.ZcShape
import com.gguf.zerocopy.ui.theme.currentPalette

/**
 * Invent "Model Dock": a live monitor for the engine rack. Shows the active
 * load attempt (via [ModelLoadMonitor]), the currently loaded model with its
 * engine config, all three engines' dock status, and the device compute
 * profile. Since native loaders report no percentage, loading renders as an
 * animated stage tracker rather than a progress bar.
 */
@Composable
fun ModelLoadingScreen(
  onBack: () -> Unit,
  onModelsClick: () -> Unit
) {
  val app = ZeroCopyApp.instance
  val colors = currentPalette()
  val models by app.modelRepository.models.collectAsState(initial = emptyList())
  val event = ModelLoadMonitor.event
  val engine = app.engineManager.getActiveEngine()
  val device = remember { app.deviceUtils.detect() }

  Column(Modifier.fillMaxSize().background(colors.Bg)) {
    // ── Header ──
    Row(
      Modifier.fillMaxWidth().padding(start = 4.dp, end = 12.dp, top = 6.dp, bottom = 4.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = onBack, modifier = Modifier.size(32.dp)) {
        Icon(Icons.Filled.ArrowBack, "Back", tint = colors.Text2, modifier = Modifier.size(18.dp))
      }
      Icon(Icons.Filled.Memory, null, tint = colors.Accent, modifier = Modifier.size(16.dp))
      Spacer(Modifier.width(6.dp))
      Text(
        "MODEL DOCK",
        fontSize = 15.sp, fontWeight = FontWeight.Bold, color = colors.Accent,
        fontFamily = FontFamily.Monospace
      )
      Spacer(Modifier.weight(1f))
      Text(
        engine?.engineName ?: "No engine",
        fontSize = 9.sp, color = colors.Text3, fontFamily = FontFamily.Monospace
      )
    }
    HorizontalDivider(color = colors.Border, thickness = 0.5.dp)

    Column(
      Modifier.fillMaxSize().verticalScroll(rememberScrollState())
        .padding(start = 14.dp, end = 14.dp, top = 12.dp, bottom = 24.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      StatusCard(colors, event, engine, models, onModelsClick)
      EngineDock(app, colors)
      ComputeCard(colors, device, engine?.config)
    }
  }
}

// ─── Status hero ───────────────────────────────────────────────────────────────
@Composable
private fun StatusCard(
  colors: ZcPalette,
  event: ModelLoadEvent,
  engine: InferenceEngine?,
  models: List<LocalModel>,
  onModelsClick: () -> Unit
) {
  val loadedPath = engine?.loadedModelPath
  val isLoaded = engine?.isModelLoaded == true && !loadedPath.isNullOrEmpty()
  val failed = event is ModelLoadEvent.Failed && !isLoaded

  Surface(
    shape = ZcShape.Lg,
    color = colors.Card,
    border = BorderStroke(0.2.dp, if (failed) colors.Red.copy(alpha = 0.45f) else colors.Border)
  ) {
    Column(
      Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 18.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      when {
        // ── Loading: animated orb + stage tracker ──
        event is ModelLoadEvent.Loading -> {
          GradientSearchingCircle(size = 84.dp)
          Spacer(Modifier.height(14.dp))
          Text(
            event.modelName, fontSize = 15.sp, fontWeight = FontWeight.Bold,
            color = colors.Text, maxLines = 1, overflow = TextOverflow.Ellipsis
          )
          Spacer(Modifier.height(4.dp))
          Text(
            event.step, fontSize = 11.sp, color = colors.Accent,
            fontFamily = FontFamily.Monospace, maxLines = 1, overflow = TextOverflow.Ellipsis
          )
          Spacer(Modifier.height(10.dp))
          Text(
            "LOADING", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = colors.Accent,
            fontFamily = FontFamily.Monospace, letterSpacing = 2.sp
          )
        }

        // ── Loaded: model card + config ──
        isLoaded -> {
          val model = models.firstOrNull { it.path == loadedPath }
          val engineVal = engine
          Icon(Icons.Filled.CheckCircle, null, tint = colors.Accent2, modifier = Modifier.size(30.dp))
          Spacer(Modifier.height(10.dp))
          Text(
            model?.name ?: loadedPath?.substringAfterLast('/') ?: "Model",
            fontSize = 15.sp, fontWeight = FontWeight.Bold, color = colors.Text,
            maxLines = 1, overflow = TextOverflow.Ellipsis
          )
          Spacer(Modifier.height(4.dp))
          Text(
            (engineVal?.engineName ?: "?") + (model?.format?.let { " · $it" } ?: ""),
            fontSize = 10.sp, color = colors.Text3
          )
          if (engineVal != null) {
            Spacer(Modifier.height(10.dp))
            Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
              val info = engineVal.modelInfo
              if (info != null) {
                InfoRow("Architecture", info.arch.ifEmpty { "unknown" }, colors)
                InfoRow("Parameters", formatParams(info.nParams), colors)
                if (info.quantization.isNotEmpty()) InfoRow("Quantization", info.quantization, colors)
                if (info.contextLength > 0) InfoRow("Native ctx", info.contextLength.toString(), colors)
              }
              InfoRow("Config ctx", engineVal.config.nCtx.toString(), colors)
              InfoRow("GPU layers", engineVal.config.nGpuLayers.toString(), colors)
              InfoRow("Threads", engineVal.config.nThreads.toString(), colors)
            }
          }
          Spacer(Modifier.height(14.dp))
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Surface(
              onClick = {
                appUnload()
                ModelLoadMonitor.clear()
              },
              shape = ZcShape.Pill,
              color = colors.CardLight,
              border = BorderStroke(0.2.dp, colors.Border)
            ) {
              Row(Modifier.padding(horizontal = 12.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Close, null, tint = colors.Red, modifier = Modifier.size(13.dp))
                Spacer(Modifier.width(5.dp))
                Text("Unload", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = colors.Red)
              }
            }
            Surface(
              onClick = onModelsClick,
              shape = ZcShape.Pill,
              color = colors.Accent.copy(alpha = 0.16f),
              border = BorderStroke(0.5.dp, colors.Accent.copy(alpha = 0.45f))
            ) {
              Row(Modifier.padding(horizontal = 12.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.SmartToy, null, tint = colors.Accent, modifier = Modifier.size(13.dp))
                Spacer(Modifier.width(5.dp))
                Text("Go to Models", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = colors.Accent)
              }
            }
          }
        }

        // ── Failed ──
        failed -> {
          val fail = event as ModelLoadEvent.Failed
          Icon(Icons.Filled.Error, null, tint = colors.Red, modifier = Modifier.size(30.dp))
          Spacer(Modifier.height(10.dp))
          Text(
            fail.modelName, fontSize = 15.sp, fontWeight = FontWeight.Bold,
            color = colors.Text, maxLines = 1, overflow = TextOverflow.Ellipsis
          )
          Spacer(Modifier.height(4.dp))
          Text(
            fail.message, fontSize = 11.sp, color = colors.Red,
            fontFamily = FontFamily.Monospace, textAlign = TextAlign.Center,
            maxLines = 3, overflow = TextOverflow.Ellipsis, modifier = Modifier.fillMaxWidth()
          )
          Spacer(Modifier.height(12.dp))
          Surface(
            onClick = onModelsClick,
            shape = ZcShape.Pill,
            color = colors.Accent.copy(alpha = 0.16f),
            border = BorderStroke(0.5.dp, colors.Accent.copy(alpha = 0.45f))
          ) {
            Row(Modifier.padding(horizontal = 12.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Filled.SmartToy, null, tint = colors.Accent, modifier = Modifier.size(13.dp))
              Spacer(Modifier.width(5.dp))
              Text("Go to Models", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = colors.Accent)
            }
          }
        }

        // ── Idle: nothing loaded ──
        else -> {
          Icon(
            Icons.Filled.SmartToy, null, tint = colors.Text3.copy(alpha = 0.5f),
            modifier = Modifier.size(36.dp)
          )
          Spacer(Modifier.height(12.dp))
          Text("No model loaded", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = colors.Text)
          Spacer(Modifier.height(4.dp))
          Text(
            "Load a model from the Models tab — its engine status, config and live load stages will appear here.",
            fontSize = 11.sp, color = colors.Text2, textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
          )
          if (event is ModelLoadEvent.Ready) {
            Spacer(Modifier.height(8.dp))
            Text(
              "Last load: ${(event as ModelLoadEvent.Ready).modelName}",
              fontSize = 9.sp, color = colors.Text3, fontFamily = FontFamily.Monospace
            )
          }
          Spacer(Modifier.height(14.dp))
          Surface(
            onClick = onModelsClick,
            shape = ZcShape.Pill,
            color = colors.Accent,
            border = BorderStroke(0.5.dp, colors.Accent)
          ) {
            Row(Modifier.padding(horizontal = 14.dp, vertical = 9.dp), verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Filled.SmartToy, null, tint = colors.Bg, modifier = Modifier.size(14.dp))
              Spacer(Modifier.width(6.dp))
              Text("Find a model", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = colors.Bg)
            }
          }
        }
      }
    }
  }
}

private fun appUnload() {
  runCatching { ZeroCopyApp.instance.engineManager.getActiveEngine()?.unloadModel() }
}

// ─── Engine dock ───────────────────────────────────────────────────────────────
@Composable
private fun EngineDock(app: ZeroCopyApp, colors: ZcPalette) {
  Surface(shape = ZcShape.Lg, color = colors.Card, border = BorderStroke(0.2.dp, colors.Border)) {
    Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Filled.Cpu, null, tint = colors.Cyan, modifier = Modifier.size(13.dp))
        Spacer(Modifier.width(6.dp))
        Text("ENGINES", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = colors.Cyan, fontFamily = FontFamily.Monospace)
      }
      listOf(app.engineManager.llamaCpp, app.engineManager.mnn, app.engineManager.liteRt).forEach { eng ->
        DockRow(eng, colors)
      }
    }
  }
}

@Composable
private fun DockRow(eng: InferenceEngine, colors: ZcPalette) {
  val loaded = eng.isModelLoaded && !eng.loadedModelPath.isNullOrEmpty()
  Row(verticalAlignment = Alignment.CenterVertically) {
    Box(
      Modifier.size(7.dp).clip(CircleShape)
        .background(if (loaded) colors.Accent2 else colors.Text3.copy(alpha = 0.45f))
    )
    Spacer(Modifier.width(8.dp))
    Column(Modifier.weight(1f)) {
      Text(
        eng.engineName, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = colors.Text,
        maxLines = 1, overflow = TextOverflow.Ellipsis
      )
      Text(
        eng.loadedModelPath?.substringAfterLast('/') ?: eng.engineType.formats.joinToString(", "),
        fontSize = 9.sp, color = colors.Text3, fontFamily = FontFamily.Monospace,
        maxLines = 1, overflow = TextOverflow.Ellipsis
      )
    }
    if (loaded) {
      Spacer(Modifier.width(8.dp))
      Text("LOADED", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = colors.Accent2, fontFamily = FontFamily.Monospace)
    }
  }
}

// ─── Compute card ──────────────────────────────────────────────────────────────
@Composable
private fun ComputeCard(colors: ZcPalette, device: DeviceInfo, config: InferenceConfig?) {
  Surface(shape = ZcShape.Lg, color = colors.Card, border = BorderStroke(0.2.dp, colors.Border)) {
    Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Filled.Speed, null, tint = colors.Cyan, modifier = Modifier.size(13.dp))
        Spacer(Modifier.width(6.dp))
        Text("COMPUTE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = colors.Cyan, fontFamily = FontFamily.Monospace)
      }
      InfoRow("SoC", device.socModel.ifEmpty { Build.MODEL }, colors)
      InfoRow("Cores", "${device.cpuCores} · big ${device.bigCores.size}", colors)
      InfoRow("RAM", "${device.totalRamMB / 1024} GB", colors)
      InfoRow(
        "Vulkan",
        if (device.hasVulkan) "available" else "unavailable",
        colors, if (device.hasVulkan) colors.Accent2 else colors.Red
      )
      if (config != null) {
        HorizontalDivider(color = colors.Border, thickness = 0.5.dp)
        InfoRow("Backend", backendLabel(config.backend, device.hasVulkan), colors)
        InfoRow("GPU layers", config.nGpuLayers.toString(), colors)
        InfoRow("Threads", config.nThreads.toString(), colors)
        InfoRow("Context", config.nCtx.toString(), colors)
        InfoRow("Batch", config.nBatch.toString(), colors)
        InfoRow("Max new", config.maxNewTokens.toString(), colors)
        InfoRow("Flash attn", if (config.flashAttention) "on" else "off", colors)
      }
    }
  }
}

// ─── Helpers ───────────────────────────────────────────────────────────────────
@Composable
private fun InfoRow(label: String, value: String, colors: ZcPalette, valueColor: Color? = null) {
  Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
    Text(
      label.uppercase(), fontSize = 9.sp, color = colors.Text3,
      fontFamily = FontFamily.Monospace, modifier = Modifier.weight(1f)
    )
    Text(
      value, fontSize = 11.sp, color = valueColor ?: colors.Text,
      fontFamily = FontFamily.Monospace, maxLines = 1, overflow = TextOverflow.Ellipsis
    )
  }
}

private fun backendLabel(backend: String, hasVulkan: Boolean): String = when (backend) {
  "gpu" -> if (hasVulkan) "GPU (Vulkan)" else "GPU (no Vulkan!)"
  "cpu" -> "CPU"
  else -> "Auto (CPU preferred)"
}

private fun formatParams(n: Long): String = when {
  n >= 1_000_000_000L -> "%.2fB".format(n / 1e9)
  n >= 1_000_000L -> "%.1fM".format(n / 1e6)
  n > 0 -> "$n"
  else -> "—"
}