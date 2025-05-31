package dev.quotes.di

import co.touchlab.kermit.Logger
import de.jensklingenberg.ktorfit.ktorfit
import dev.quotes.network.QuotesService
import dev.quotes.network.createQuotesService
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import io.ktor.client.plugins.logging.Logger as KtorLogger

@AppScope
@Component
abstract class CommonGraph(
    private val engineFactory: HttpClientEngineFactory<*>,
) {
    @Provides
    @AppScope
    fun providesLogger() = Logger.withTag("Quotes")

    @Provides
    @AppScope
    fun providesJson() = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    @Provides
    @AppScope
    fun providesHttpClient(
        json: Json,
        log: Logger,
    ) = HttpClient(engineFactory) {
        install(HttpTimeout)
        install(HttpCache)
        install(ContentNegotiation) {
            json(json = json)
        }
        install(Logging) {
            logger = object : KtorLogger {
                override fun log(message: String) {
                    log.d("HTTP Client: $message")
                }
            }
            level = LogLevel.INFO
        }
    }

    @Provides
    @AppScope
    fun providesQuotesService(
        httpClient: HttpClient
    ): QuotesService = ktorfit {
        baseUrl("https://dummyjson.com/")
        httpClient(httpClient)
    }.createQuotesService()

    companion object {
        private lateinit var instance: CommonGraph

        fun init(
            graph: () -> CommonGraph
        ) {
            if (!::instance.isInitialized) {
                instance = graph()
            }
        }

        fun get() = instance
    }
}
