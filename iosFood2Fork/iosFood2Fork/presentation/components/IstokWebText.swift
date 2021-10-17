//
//  IstokWebText.swift
//  iosFood2Fork
//
//  Created by Vusi Moyo on 2021/10/17.
//  Copyright © 2021 orgName. All rights reserved.
//
//  We can use this setup to set the default font for our project

import SwiftUI

struct IstokWebText: View {
    
    private let text: String
    private let size: CGFloat
    
    init(_ text: String, size: CGFloat = 15) {
        self.text = text
        self.size = size
    }
    
    var body: some View {
        Text(text)
            .font(Font.custom("IstokWeb", size: size))
    }
}

//struct IstokWebText_Previews: PreviewProvider {
//    static var previews: some View {
//        IstokWebText()
//    }
//}
