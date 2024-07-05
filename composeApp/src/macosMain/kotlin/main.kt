import androidx.compose.ui.window.Window
import androidx.lifecycle.viewmodel.CreationExtras
import dev.quotes.di.CommonGraph
import dev.quotes.di.create
import dev.quotes.ui.QuotesViewModel
import dev.quotes.ui.UiGraph
import io.ktor.client.engine.darwin.Darwin
import platform.AppKit.NSApp
import platform.AppKit.NSApplication

fun main() {
    NSApplication.sharedApplication()
    CommonGraph.init(CommonGraph::class.create(Darwin))
    Window(
        title = "Programming Quotes",
    ) {
        App(
            viewModel = UiGraph.build().ourViewModelFactory
                .create(QuotesViewModel::class, CreationExtras.Empty)
        )
    }
    NSApp?.run()
}
