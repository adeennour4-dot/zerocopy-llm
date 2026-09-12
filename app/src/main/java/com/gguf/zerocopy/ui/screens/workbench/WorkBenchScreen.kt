package com.gguf.zerocopy.ui.screens.workbench

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkbenchScreen() {
    var ragPrompt by remember { mutableStateOf("") }
    var contextText by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("ZeroCopy Workbench & RAG") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = contextText,
                onValueChange = { contextText = it },
                label = { Text("Injected Context Document (Zero-Copy Buffer)") },
                modifier = Modifier.fillMaxWidth().weight(1f)
            )

            OutlinedTextField(
                value = ragPrompt,
                onValueChange = { ragPrompt = it },
                label = { Text("Query / Instruction") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { /* Hook to backend */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Execute In-Memory Diagnostic")
            }
        }
    }
}
