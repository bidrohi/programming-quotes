//
//  LoadingView.swift
//  iosApp
//
//  Created by Saud Khan on 8/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct LoadingView: View {
    var body: some View {
        if #available(iOS 15.0, *) {
            ProgressView()
                .tint(.accentColor)
        } else {
            ProgressView()
                .progressViewStyle(CircularProgressViewStyle(tint: .purple))
        }
    }
}
