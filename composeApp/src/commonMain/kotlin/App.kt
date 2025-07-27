import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.quotes.network.model.QuoteWithAuthor
import dev.quotes.ui.QuotesComposeViewModel
import dev.quotes.ui.QuotesUseCase
import dev.quotes.ui.UiGraph
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    viewModel: QuotesComposeViewModel = viewModel(factory = UiGraph.build().ourViewModelFactory)
) {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ) {
            val state by viewModel.uiState.collectAsState(QuotesUseCase.UiState.Loading)
            val contentModifier = Modifier.fillMaxSize()
                .safeContentPadding()
            when (val s = state) {
                is QuotesUseCase.UiState.Loading -> LoadingScreen(contentModifier)
                is QuotesUseCase.UiState.Error -> ErrorScreen(s.errorMessage, contentModifier)
                is QuotesUseCase.UiState.ShowContent -> QuotesScreen(s.quotes, contentModifier)
            }
        }
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(
    errorMessage: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = errorMessage,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun QuotesScreen(
    quotes: List<QuoteWithAuthor>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState { quotes.size }
    HorizontalPager(
        state = pagerState,
        pageSize = PageSize.Fill,
        pageSpacing = 1.dp,
        modifier = modifier,
    ) { index ->
        val q = quotes[index]
        QuoteView(
            quote = q,
            modifier = Modifier.fillMaxSize()
                .padding(16.dp)
        )
    }
}

@Composable
private fun QuoteView(
    quote: QuoteWithAuthor,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.border(8.dp, MaterialTheme.colors.primary, RoundedCornerShape(16.dp))
            .padding(32.dp),
    ) {
        Text(
            text = quote.quote,
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center),
        )
        Text(
            text = quote.author,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}
