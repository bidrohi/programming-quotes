package dev.quotes.ui

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.StateFlow
import kotlin.native.ObjCName

interface TriggerViewModel<Trigger, UiState> {
    @NativeCoroutinesState
    val uiState: StateFlow<UiState>

    fun sendTrigger(
        @ObjCName("_") trigger: Trigger,
    )
}
