package com.gguf.zerocopy.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    var useVulkan by remember { mutableStateOf(true) }
    var threadCount by remember { floatStateOf(4f) }
    var contextSize by remember { floatStateOf(4096f) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Hardware Engine Settings") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Vulkan GPU Offloading", style = MaterialTheme.typography.bodyLarge)
                    Text("Offload layers directly to Adreno/Mali GPU", style = MaterialTheme.typography.bodySmall)
                }
                Switch(checked = useVulkan, onCheckedChange = { useVulkan = it })
            }

            HorizontalDivider()

            Column {
                Text("CPU Threads: ${threadCount.toInt()}", style = MaterialTheme.typography.bodyLarge)
                Slider(
                    value = threadCount,
                    onValueChange = { threadCount = it },
                    valueRange = 1f..8f,
                    steps = 6
                )
            }

            Column {
                Text("Context Window Size: ${contextSize.toInt()} Tokens", style = MaterialTheme.typography.bodyLarge)
                Slider(
                    value = contextSize,
                    onValueChange = { contextSize = it },
                    valueRange = 1024f..16384f,
                    steps = 3
                )
            }
        }
    }
}
