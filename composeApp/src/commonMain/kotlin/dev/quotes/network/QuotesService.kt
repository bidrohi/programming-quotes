package dev.quotes.network

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesIgnore
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query
import dev.quotes.network.model.QuotesResponse

interface QuotesService {
    @GET("quotes")
    @NativeCoroutinesIgnore
    suspend fun getQuotes(
        @Query("limit") count: Long = 10,
        @Query("skip") startAt: Long = 0,
    ): QuotesResponse
}
