package dev.quotes

import android.app.Application
import dev.quotes.di.CommonGraph
import dev.quotes.di.create
import io.ktor.client.engine.android.Android

class QuotesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CommonGraph.init {
            CommonGraph::class.create(Android)
        }
    }
}
