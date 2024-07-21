package dev.quotes.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

abstract class FlowUseCase<UiState>(
    protected val useCaseScope: CoroutineScope,
) {
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
