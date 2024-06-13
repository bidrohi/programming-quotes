package dev.quotes.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuoteWithAuthor(
    @SerialName("author")
    val author: String = "",
    @SerialName("quote")
    val quote: String = ""
)
