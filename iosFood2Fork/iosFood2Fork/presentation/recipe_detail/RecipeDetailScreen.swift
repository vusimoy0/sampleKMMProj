//
//  RecipeDetailScreen.swift
//  iosFood2Fork
//
//  Created by Vusi Moyo on 2021/10/17.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct RecipeDetailScreen: View {
    
    //dependencies
    private let cacheModule: CacheModule
    private let getRecipeModule: GetRecipeModule
    private let recipeId: Int
    private let dateTimeUtil = DateTimeUtil()
    
    @ObservedObject var viewModel: RecipeDetailViewModel
    
    init(recipeId: Int, cacheModule: CacheModule) {
        self.recipeId = recipeId
        self.cacheModule = cacheModule
        self.getRecipeModule = GetRecipeModule(cacheModule: self.cacheModule)
        viewModel = RecipeDetailViewModel(recipeId: self.recipeId, getRecipe: self.getRecipeModule.getRecipe)
    }
    
    var body: some View {
        if viewModel.state.recipe != nil {
            RecipeView(recipe: viewModel.state.recipe!, dateUtil: dateTimeUtil, message: viewModel.state.queue.peek(), onTriggerEvent: viewModel.onTriggeredEvent)
        } else {
            IstokWebText("Unable to retrieve the recipe details.")
        }
    }
}

//struct RecipeDetailScreen_Previews: PreviewProvider {
//    static var previews: some View {
//        RecipeDetailScreen()
//    }
//}
