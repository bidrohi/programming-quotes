//
//  ErrorView.swift
//  iosApp
//
//  Created by Saud Khan on 8/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ErrorView: View {
    
    private let errorMessage: String
    
    init (_ errorMessage: String) {
        self.errorMessage = errorMessage
    }
    
    var body: some View {
        Text(errorMessage)
    }
}
