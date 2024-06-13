package dev.quotes.network

import de.jensklingenberg.ktorfit.http.GET
import dev.quotes.network.model.QuoteWithAuthor

interface QuotesService {
    @GET("bulk")
    suspend fun getQuotes(): List<QuoteWithAuthor>
}
