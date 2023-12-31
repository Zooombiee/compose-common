/*
 * Copyright (C) 2022 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cxc.common.molecule

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cxc.common.molecule.RecompositionMode.ContextClock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow

abstract class MoleculeViewModel<Event:UIEvent, Model:UIState> : ViewModel() {
  private val scope = CoroutineScope(viewModelScope.coroutineContext + AndroidUiDispatcher.Main)

  // Events have a capacity large enough to handle simultaneous UI events, but
  // small enough to surface issues if they get backed up for some reason.
  private val _events = MutableSharedFlow<Event>(extraBufferCapacity = 20)

  val models: StateFlow<Model> by lazy(LazyThreadSafetyMode.NONE) {
    scope.launchMolecule(mode = ContextClock) {
      models(_events)
    }
  }

  val events = _events
  fun take(event: Event) {
    if (!_events.tryEmit(event)) {
//      error("Event buffer overflow.")
    }
  }

  @Composable
  protected abstract fun models(events: Flow<Event>): Model
}
