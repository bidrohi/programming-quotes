package dev.quotes.data

import com.bidyut.tech.bhandar.Bhandar
import com.bidyut.tech.bhandar.DataFetcher
import com.bidyut.tech.bhandar.ReadResult
import com.bidyut.tech.bhandar.Storage
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import dev.quotes.di.AppScope
import dev.quotes.network.QuotesService
import dev.quotes.network.model.QuoteWithAuthor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import me.tatarka.inject.annotations.Inject

@Inject
@AppScope
class QuotesRepository(
    private val service: QuotesService,
    private val memStorage: MutableStateFlow<List<QuoteWithAuthor>?> = MutableStateFlow(null),
) : Bhandar<Any, List<QuoteWithAuthor>>(
    fetcher = DataFetcher.of {
        try {
            Result.success(service.getQuotes())
        } catch (e: Exception) {
            Result.failure(e)
        }
    },
    storage = Storage.of(
        isValid = { !it.isNullOrEmpty() },
        read = {
            memStorage
        },
        write = { _, newValue -> memStorage.emit(newValue) }
    )
) {
    @NativeCoroutines
    fun getQuotes(): Flow<ReadResult<List<QuoteWithAuthor>>> = cached(
        Unit,
        refresh = false
    )
}
