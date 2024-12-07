//
//  QuotesView.swift
//  iosApp
//
//  Created by Saud Khan on 8/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import QuotesShared

struct QuotesView: View {
    
    private let quotes: [QuoteWithAuthor]
    
    init (_ quotes: [QuoteWithAuthor]) {
        self.quotes = quotes
    }
    
    var body: some View {
        TabView {
            ForEach(0..<quotes.count) { i in
                QuoteView(quotes[i])
            }.padding(.all, 16)
        }
        .padding(.bottom, 86)
        .frame(width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height - 40)
        .tabViewStyle(PageTabViewStyle())
    }
}

struct QuoteView: View {
    
    private let quote: QuoteWithAuthor
    
    init (_ quote: QuoteWithAuthor) {
        self.quote = quote
    }
    
    var body: some View {
        VStack {
            Spacer()
            Text(quote.quote)
                .font(.system(size: 36))
                .multilineTextAlignment(.center)
            Spacer()
            Text(quote.author)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .padding()
        .cornerRadius(16)
        .overlay(
            RoundedRectangle(cornerRadius: 20)
                .stroke(.blue, lineWidth: 8)
        )
    }
}
