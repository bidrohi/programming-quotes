//
//  QuotesViewModel.swift
//  iosApp
//
//  Created by Saud Khan on 2024-07-03.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Combine
import QuotesShared
import KMPNativeCoroutinesCombine

class QuotesViewModel: ObservableObject {

    @Published var uiState: QuotesUseCaseUiState = QuotesUseCaseUiStateLoading.shared

    private let vm = UiGraph.companion.build().makeQuotesNativeViewModel()
    private var cancellable: AnyCancellable?
    
    func startMonitoring() {
        // Create an AnyPublisher for your flow
        let publisher = createPublisher(for: vm.uiState)

        // Now use this publisher as you would any other
        self.cancellable = publisher.sink { completion in
            print("Received completion: \(completion)")
        } receiveValue: { value in
            print("Received value: \(value)")
            self.uiState = value
        }
    }
    
    func stopMonitoring() {
        self.cancellable?.cancel()
        self.cancellable = nil
    }
}
