//
//  ModeSelector.swift
//  iosApp
//
//  Created by Saud Khan on 2024-12-08.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ModeSelectorView: View {
    @State private var path = NavigationPath()
    
    var body: some View {
        NavigationStack(path: $path) {
            VStack {
                Button(action: {
                    path.append("1")
                }) {
                    Text("Implementation 1")
                        .font(.system(size: 24))
                        .frame(maxWidth: .infinity, maxHeight: .infinity)
                }
                .buttonStyle(.borderedProminent)
                .padding(20)

                Button(action: {
                    path.append("2")
                }) {
                    Text("Implementation 2")
                        .font(.system(size: 24))
                        .frame(maxWidth: .infinity, maxHeight: .infinity)
                }
                .buttonStyle(.borderedProminent)
                .padding(20)
            }
            .navigationTitle("Programming Quotes")
            .navigationDestination(for: String.self) { value in
                if value == "1" {
                    ContentView()
                } else if value == "2" {
                    ContentNativeView()
                }
            }
        }
    }
}
