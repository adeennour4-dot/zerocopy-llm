package com.gguf.zerocopy.data.local

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * App-wide, UI-observable record of the most recent model-load attempt.
 *
 * The native loaders report no incremental percentage, so this broadcasts the
 * coarse lifecycle (idle -> loading -> ready / failed) plus the current stage
 * string. Written by the loaders (Models tab, token-config auto-load), read by
 * the Invent Model Dock and anything else that wants to render "loading".
 */
sealed interface ModelLoadEvent {
  object Idle : ModelLoadEvent
  data class Loading(val modelName: String, val step: String) : ModelLoadEvent
  data class Ready(val modelPath: String, val modelName: String) : ModelLoadEvent
  data class Failed(val modelName: String, val message: String) : ModelLoadEvent
}

object ModelLoadMonitor {
  var event by mutableStateOf<ModelLoadEvent>(ModelLoadEvent.Idle)
    private set

  fun begin(modelName: String, step: String) {
    event = ModelLoadEvent.Loading(modelName, step)
  }

  fun step(step: String) {
    val cur = event
    if (cur is ModelLoadEvent.Loading) event = ModelLoadEvent.Loading(cur.modelName, step)
  }

  fun ready(modelPath: String, modelName: String) {
    event = ModelLoadEvent.Ready(modelPath, modelName)
  }

  fun failed(modelName: String, message: String) {
    event = ModelLoadEvent.Failed(modelName, message)
  }

  fun clear() {
    event = ModelLoadEvent.Idle
  }
}