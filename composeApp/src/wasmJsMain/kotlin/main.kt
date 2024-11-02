import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import dev.quotes.di.CommonGraph
import dev.quotes.di.create
import io.ktor.client.engine.js.Js
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CommonGraph.init(CommonGraph::class.create(Js))
    ComposeViewport(document.body!!) {
        App()
    }
}
