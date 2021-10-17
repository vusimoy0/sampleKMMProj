package za.co.kernelpanic.food2forkkmm.di

import za.co.kernelpanic.food2forkkmm.interactors.recipe_detail.GetRecipe

class GetRecipeModule(
    private val cacheModule: CacheModule
) {

    val getRecipe: GetRecipe by lazy {
        GetRecipe(recipeCache = cacheModule.recipeCache)
    }
}