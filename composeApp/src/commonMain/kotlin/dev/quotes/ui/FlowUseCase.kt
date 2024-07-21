package dev.quotes.ui

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

abstract class FlowUseCase<UiState>(
    protected val useCaseScope: CoroutineScope,
) {
    @NativeCoroutinesState
    val uiState by lazy {
        makeStateFlow()
    }

    protected open fun makeStateFlow() = makeFlow().stateIn(
        useCaseScope,
        SharingStarted.Lazily,
        makeInitialState()
    )

    protected abstract fun makeInitialState(): UiState

    protected abstract fun makeFlow(): Flow<UiState>
}
