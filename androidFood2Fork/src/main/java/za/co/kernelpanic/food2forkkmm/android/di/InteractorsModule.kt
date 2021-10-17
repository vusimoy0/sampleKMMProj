package za.co.kernelpanic.food2forkkmm.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import za.co.kernelpanic.food2forkkmm.datasource.cache.RecipeCache
import za.co.kernelpanic.food2forkkmm.datasource.network.RecipeService
import za.co.kernelpanic.food2forkkmm.domain.model.Recipe
import za.co.kernelpanic.food2forkkmm.interactors.recipe_detail.GetRecipe
import za.co.kernelpanic.food2forkkmm.interactors.recipe_list.SearchRecipes
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorsModule {

    @Singleton
    @Provides
    fun providesSearchRecipes(
        recipeService: RecipeService,
        recipeCache: RecipeCache
    ): SearchRecipes {
        return SearchRecipes(recipeService = recipeService, recipeCache = recipeCache)
    }

    @Singleton
    @Provides
    fun providesRecipe(recipeCache: RecipeCache): GetRecipe {
        return GetRecipe(recipeCache = recipeCache)
    }
}