package dev.quotes.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.quotes.di.CommonGraph
import dev.quotes.di.RetainedScope
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@RetainedScope
@Component
abstract class UiGraph(
    @Component protected val commonGraph: CommonGraph,
) {
    abstract val ourViewModelFactory: ViewModelProvider.Factory

    @Provides
    @RetainedScope
    protected fun provideViewModelProviderFactory(
        quotesViewModel: () -> QuotesViewModel,
    ): ViewModelProvider.Factory {
        return viewModelFactory {
            addInitializer(QuotesViewModel::class) {
                quotesViewModel()
            }
        }
    }

    companion object {
        fun build() = UiGraph::class.create(
            CommonGraph.get(),
        )
    }
}
