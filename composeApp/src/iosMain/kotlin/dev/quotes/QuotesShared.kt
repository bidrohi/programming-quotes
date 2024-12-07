package dev.quotes

import App
import androidx.compose.ui.window.ComposeUIViewController
import dev.quotes.di.CommonGraph
import dev.quotes.di.create
import io.ktor.client.engine.darwin.Darwin

fun initialize() {
    CommonGraph.init {
        CommonGraph::class.create(Darwin)
    }
}

fun makeComposeViewController() = ComposeUIViewController{
    App()
}
