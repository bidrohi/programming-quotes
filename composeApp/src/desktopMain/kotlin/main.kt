import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.quotes.di.CommonGraph
import dev.quotes.di.create
import io.ktor.client.engine.cio.CIO

fun main() = application {
    CommonGraph.init {
        CommonGraph::class.create(CIO)
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Programming Quotes",
    ) {
        App()
    }
}
