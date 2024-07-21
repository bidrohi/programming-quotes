package dev.quotes.ui

import com.bidyut.tech.bhandar.ReadResult
import dev.quotes.data.QuotesRepository
import dev.quotes.network.model.QuoteWithAuthor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.native.ObjCName

class QuotesUseCase(
    private val repository: QuotesRepository,
    useCaseScope: CoroutineScope,
) : TriggerUseCase<Unit, QuotesUseCase.UiState>(useCaseScope) {

    constructor(
        @ObjCName("_") repository: QuotesRepository,
    ) : this(repository, CoroutineScope(Dispatchers.Main + SupervisorJob()))

    override fun makeInitialState() = UiState.Loading

    override fun makeFlow(): Flow<UiState> {
        return repository.getQuotes().map { response ->
            when (response) {
                is ReadResult.Loading -> UiState.Loading
                is ReadResult.Error -> UiState.Error(response.errorMessage)
                is ReadResult.Data -> UiState.ShowContent(response.data.orEmpty())
            }
        }
    }

    override suspend fun handleTriggers(trigger: Unit) {
        // No triggers
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
