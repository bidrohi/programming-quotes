package dev.quotes.network

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesIgnore
import de.jensklingenberg.ktorfit.http.GET
import dev.quotes.network.model.QuoteWithAuthor

interface QuotesService {
    @GET("bulk")
    @NativeCoroutinesIgnore
    suspend fun getQuotes(): List<QuoteWithAuthor>
}
