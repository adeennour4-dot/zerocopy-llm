package com.gguf.zerocopy.ui.screens.models

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class GgufModelItem(
    val id: String,
    val name: String,
    val sizeBytes: String,
    val quantization: String,
    val isLoaded: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModelManagerScreen() {
    val dummyModels = remember {
        mutableStateListOf(
            GgufModelItem("1", "Qwen2.5-3B-Instruct-Q4_K_M.gguf", "1.9 GB", "Q4_K_M", true),
            GgufModelItem("2", "Llama-3.2-1B-Instruct-Q8_0.gguf", "1.1 GB", "Q8_0", false),
            GgufModelItem("3", "Gemma-2-2B-it-Q5_K_S.gguf", "1.6 GB", "Q5_K_S", false)
        )
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Local Models") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("RAM / VRAM Diagnostics", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(12.dp))
                    LinearProgressIndicator(
                        progress = { 0.45f },
                        modifier = Modifier.fillMaxWidth().height(8.dp),
                        strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Allocated: 3.2 GB / 8.0 GB", style = MaterialTheme.typography.labelMedium)
                }
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(dummyModels, key = { it.id }) { model ->
                    ModelCard(
                        model = model,
                        onLoadToggle = {
                            val index = dummyModels.indexOf(model)
                            dummyModels[index] = model.copy(isLoaded = !model.isLoaded)
                        },
                        modifier = Modifier.animateItem() 
                    )
                }
            }
        }
    }
}

@Composable
fun ModelCard(model: GgufModelItem, onLoadToggle: () -> Unit, modifier: Modifier = Modifier) {
    val buttonContainerColor by animateColorAsState(
        targetValue = if (model.isLoaded) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primary,
        label = "buttonColor"
    )
    val buttonTextColor by animateColorAsState(
        targetValue = if (model.isLoaded) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onPrimary,
        label = "buttonTextColor"
    )

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(animationSpec = spring(stiffness = Spring.StiffnessLow)), 
        colors = CardDefaults.cardColors(
            containerColor = if (model.isLoaded) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (model.isLoaded) 4.dp else 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(model.name, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "${model.sizeBytes} • ${model.quantization}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = onLoadToggle,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonContainerColor,
                        contentColor = buttonTextColor
                    )
                ) {
                    Text(if (model.isLoaded) "Unload" else "Load")
                }
            }

            if (model.isLoaded) {
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Status: Running on Vulkan", style = MaterialTheme.typography.labelMedium)
                    Text("Ctx: 4096", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}
