import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import dev.quotes.ui.QuotesViewModel
import dev.quotes.ui.UiGraph
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    viewModel: QuotesViewModel = viewModel(factory = UiGraph.build().ourViewModelFactory)
) {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ) {
            val state by viewModel.uiState.collectAsState(QuotesViewModel.UiState.Loading)
            val contentModifier = Modifier.fillMaxSize()
            when (val s = state) {
                is QuotesViewModel.UiState.Loading -> LoadingScreen(contentModifier)
                is QuotesViewModel.UiState.Error -> ErrorScreen(s.errorMessage, contentModifier)
                is QuotesViewModel.UiState.ShowContent -> QuotesScreen(s.quotes, contentModifier)
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

@OptIn(ExperimentalFoundationApi::class)
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
    ) {
        val q = quotes[it]
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(16.dp)
                .border(8.dp, MaterialTheme.colors.primary, RoundedCornerShape(16.dp))
                .padding(32.dp),
        ) {
            Text(
                text = q.quote,
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center),
            )
            Text(
                text = q.author,
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}
