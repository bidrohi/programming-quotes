package dev.quotes.data

import app.cash.turbine.test
import com.bidyut.tech.bhandar.ReadResult
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode.Companion.exactly
import dev.mokkery.verify.VerifyMode.Companion.not
import dev.mokkery.verifySuspend
import dev.quotes.network.QuotesService
import dev.quotes.network.model.QuoteWithAuthor
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class QuotesRepositoryTest {
    @Test
    fun canProcessSuccess() = runTest {
        val response = listOf(
            QuoteWithAuthor(
                quote = "quote",
                author = "author"
            )
        )
        val service = mock<QuotesService> {
            everySuspend { getQuotes() } returns response
        }

        val repository = QuotesRepository(service)
        repository.getQuotes().test {
            assertIs<ReadResult.Loading<*>>(awaitItem())
            val result = awaitItem()
            assertIs<ReadResult.Success<*>>(result)
            assertEquals(response, result.data)

            verifySuspend(exactly(1)) { service.getQuotes() }
        }

        // Fetching again will serve from cache
        repository.getQuotes().test {
            assertIs<ReadResult.Loading<*>>(awaitItem())
            val result = awaitItem()
            assertIs<ReadResult.Cache<*>>(result)
            assertEquals(response, result.data)

            verifySuspend(not) { service.getQuotes() }
        }
    }

    @Test
    fun canProcessFailure() = runTest {
        val service = mock<QuotesService> {
            everySuspend { getQuotes() } throws Exception("mock error")
        }

        val repository = QuotesRepository(service)
        repository.getQuotes().test {
            assertIs<ReadResult.Loading<*>>(awaitItem())
            val result = awaitItem()
            assertIs<ReadResult.Error<*>>(result) // from network

            verifySuspend(exactly(1)) { service.getQuotes() }
        }

        // Since it was an error, there should be no cache in memory
        repository.getQuotes().test {
            assertIs<ReadResult.Loading<*>>(awaitItem())
            val result = awaitItem()
            assertIs<ReadResult.Error<*>>(result) // from network

            verifySuspend(exactly(1)) { service.getQuotes() }
        }
    }
}
