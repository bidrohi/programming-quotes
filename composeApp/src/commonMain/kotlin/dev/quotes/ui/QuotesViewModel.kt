package dev.quotes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import dev.quotes.data.QuotesRepository
import dev.quotes.di.RetainedScope
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

@Inject
@RetainedScope
class QuotesViewModel(
    repository: QuotesRepository
) : ViewModel() {

    private val useCase = QuotesUseCase(repository, viewModelScope)

    @NativeCoroutines
    val uiState: Flow<QuotesUseCase.UiState>
        get() = useCase.uiState
}
