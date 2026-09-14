package com.gguf.zerocopy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gguf.zerocopy.ui.theme.ZcPalette
import com.gguf.zerocopy.ui.theme.ZcShape

/** Chat-template options shared by the Settings screen and per-model config. */
val CHAT_TEMPLATE_OPTIONS = listOf(
  "auto" to "Auto-detect",
  "chatml" to "ChatML",
  "gemma" to "Gemma",
  "llama3" to "Llama 3",
  "deepseek" to "DeepSeek",
  "qwen" to "Qwen",
  "mistral" to "Mistral",
  "phi" to "Phi"
)

/** Dropdown used to pick a chat template (global settings + per-model config). */
@Composable
fun ChatTemplateSelector(
  current: String,
  onChange: (String) -> Unit,
  colors: ZcPalette,
  modifier: Modifier = Modifier
) {
  var expanded by remember { mutableStateOf(false) }
  val label = CHAT_TEMPLATE_OPTIONS.firstOrNull { it.first == current }?.second ?: current
  Box(modifier) {
    OutlinedButton(
      onClick = { expanded = true },
      shape = ZcShape.Pill,
      colors = ButtonDefaults.outlinedButtonColors(contentColor = colors.Text)
    ) {
      Text(label, fontSize = 12.sp, fontFamily = FontFamily.SansSerif, color = colors.Text)
      Spacer(Modifier.width(6.dp))
      Icon(Icons.Filled.ArrowDropDown, null, tint = colors.Text3)
    }
    DropdownMenu(
      expanded = expanded,
      onDismissRequest = { expanded = false },
      modifier = Modifier.background(colors.Card)
    ) {
      CHAT_TEMPLATE_OPTIONS.forEach { (value, display) ->
        DropdownMenuItem(
          text = { Text(display, fontSize = 13.sp, color = colors.Text, fontFamily = FontFamily.SansSerif) },
          leadingIcon = if (value == current) {
            { Text("✓", fontSize = 13.sp, color = colors.Accent) }
          } else null,
          onClick = { expanded = false; onChange(value) }
        )
      }
    }
  }
}