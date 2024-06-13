package dev.quotes.ui

import androidx.lifecycle.ViewModel
import com.bidyut.tech.bhandar.ReadResult
import dev.quotes.data.QuotesRepository
import dev.quotes.di.RetainedScope
import dev.quotes.network.model.QuoteWithAuthor
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

@Inject
@RetainedScope
class QuotesViewModel(
    repository: QuotesRepository
) : ViewModel() {

    val uiState = repository.getQuotes().map { response ->
        when (response) {
            is ReadResult.Loading -> UiState.Loading
            is ReadResult.Error -> UiState.Error(response.errorMessage)
            is ReadResult.Data -> UiState.ShowContent(response.data.orEmpty())
        }
    }

    sealed interface UiState {
        data object Loading : UiState

        data class Error(
            val errorMessage: String,
        ) : UiState

        data class ShowContent(
            val quotes: List<QuoteWithAuthor>,
        ) : UiState
    }
}
