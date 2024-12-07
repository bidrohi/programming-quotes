//
//  ContentNativeView.swift
//  iosApp
//
//  Created by Saud Khan on 2024-07-03.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import QuotesShared

struct ContentNativeView: View {
    @ObservedObject var viewModel = QuotesViewModel()
    
    var body: some View {
        ZStack {
            let uiState = viewModel.uiState
            switch uiState {
            case is QuotesUseCaseUiStateLoading:
                LoadingView()
            case is QuotesUseCaseUiStateError:
                let errorState = uiState as! QuotesUseCaseUiStateError
                ErrorView(errorState.errorMessage)
            case is QuotesUseCaseUiStateShowContent:
                let content = uiState as! QuotesUseCaseUiStateShowContent
                ScrollView {
                    LazyHStack {
                        QuotesView(content.quotes)
                    }
                }
            default:
                ErrorView("Should not get here")
            }
        }
        .onAppear {
            viewModel.startMonitoring()
        }
    }
}
