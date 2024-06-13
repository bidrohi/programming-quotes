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
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class QuotesServiceTest {
    @Test
    fun testGetQuotes() {
        val engine = MockEngine { request ->
            respond(
                content = ByteReadChannel(
                    """[
                        {
                            "author": "Douglas Crockford",
                            "quote": "Mathematics is important in programming, but it’s just one of a lot of things that are important. If you overemphasize the math then you underemphasize stuff which might be even more important, such as literacy."
                        },
                        {
                            "author": "Martin Fowler",
                            "quote": "There are few things more frustrating or time wasting than debugging. Wouldn't it be a hell of a lot quicker if we just didn't create the bugs in the first place?"
                        },
                        {
                            "author": "James Gleick",
                            "quote": "Computer programs are the most intricate, delicately balanced and finely interwoven of all the products of human industry to date."
                        },
                        {
                            "author": "Douglas Crockford",
                            "quote": "JavaScript, purely by accident, has become the most popular programming language in the world."
                        }
                    ]""".trimIndent()
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
        val quotes = runBlocking {
            service.getQuotes()
        }
        assertEquals(4, quotes.size)
    }
}
