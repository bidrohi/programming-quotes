package dev.quotes.ui

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import dev.quotes.data.QuotesRepository
import dev.quotes.di.RetainedScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

@Inject
@RetainedScope
class QuotesNativeViewModel(
    repository: QuotesRepository
) {
    private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val useCase = QuotesUseCase(repository, viewModelScope)

    @NativeCoroutines
    val uiState: Flow<QuotesUseCase.UiState>
        get() = useCase.uiState

    fun sendTrigger(event: Unit) {
        useCase.sendTrigger(event)
    }
}
