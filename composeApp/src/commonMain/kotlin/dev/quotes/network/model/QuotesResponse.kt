package dev.quotes.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuotesResponse(
    @SerialName("quotes")
    val quotes: List<QuoteWithAuthor> = emptyList(),
    @SerialName("total")
    val totalAvailable: Long = 0,
)
