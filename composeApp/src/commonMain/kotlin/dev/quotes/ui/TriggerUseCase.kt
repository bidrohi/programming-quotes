package dev.quotes.ui

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesIgnore
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

abstract class TriggerUseCase<Trigger, UiState>(
    @ObjCName("_") protected val useCaseScope: CoroutineScope,
) : TriggerViewModel<Trigger, UiState> {
    @NativeCoroutinesState
    override val uiState: StateFlow<UiState> by lazy {
        makeFlow().stateIn(
            useCaseScope,
            SharingStarted.Lazily,
            makeInitialState()
        )
    }

    private val triggerFlow: MutableSharedFlow<Trigger> = MutableSharedFlow()

    override fun sendTrigger(
        trigger: Trigger,
    ) {
        useCaseScope.launch {
            triggerFlow.emit(trigger)
        }
    }

    private fun subscribeToTriggers() {
        useCaseScope.launch {
            triggerFlow.collect {
                handleTriggers(it)
            }
        }
    }

    init {
        subscribeToTriggers()
    }

    protected abstract fun makeInitialState(): UiState

    @NativeCoroutinesIgnore
    protected abstract fun makeFlow(): Flow<UiState>

    @NativeCoroutinesIgnore
    protected abstract suspend fun handleTriggers(
        trigger: Trigger,
    )
}
