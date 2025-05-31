package dev.quotes.network

import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class QuotesServiceTest {
    @Test
    fun testGetQuotes() = runTest {
        val engine = MockEngine { request ->
            respond(
                content = ByteReadChannel(
                    """{
                        "quotes": [
                            {
                                "id": 1,
                                "quote": "Your heart is the size of an ocean. Go find yourself in its hidden depths.",
                                "author": "Rumi"
                            },
                            {
                                "id": 2,
                                "quote": "The Bay of Bengal is hit frequently by cyclones. The months of November and May, in particular, are dangerous in this regard.",
                                "author": "Abdul Kalam"
                            },
                            {
                                "id": 3,
                                "quote": "Thinking is the capital, Enterprise is the way, Hard Work is the solution.",
                                "author": "Abdul Kalam"
                            },
                            {
                                "id": 4,
                                "quote": "If You Can'T Make It Good, At Least Make It Look Good.",
                                "author": "Bill Gates"
                            },
                            {
                                "id": 5,
                                "quote": "Heart be brave. If you cannot be brave, just go. Love's glory is not a small thing.",
                                "author": "Rumi"
                            }
                        ],
                        "total": 1454,
                        "skip": 0,
                        "limit": 5
                    }""".trimIndent()
                ),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val service = ktorfit {
            baseUrl("https://programming-quotesapi.vercel.app/api/")
            httpClient(HttpClient(engine) {
                install(ContentNegotiation) {
                    json(json = Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                    })
                }
            })
        }.createQuotesService()
        val response = service.getQuotes()
        assertEquals(5, response.quotes.size)
    }
}
