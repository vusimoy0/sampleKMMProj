//
//  FoodCategoryChip.swift
//  iosFood2Fork
//
//  Created by Vusi Moyo on 2021/10/16.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct FoodCategoryChip: View {
    
    private let category: String
    private let isSelected: Bool
    
    init(category: String, isSelected: Bool = false) {
        self.category = category
        self.isSelected = isSelected
    }
    
    var body: some View {
        HStack {
            IstokWebText(category) //todo - update font
                .padding(8)
                .background(isSelected ? Color.gray : Color.blue) //todo - update gray color
                .foregroundColor(Color.white)
        }
        .cornerRadius(10)
    }
}

struct FoodCategoryChip_Previews: PreviewProvider {
    static var previews: some View {
        FoodCategoryChip(category: "Beef", isSelected: false)
    }
}
