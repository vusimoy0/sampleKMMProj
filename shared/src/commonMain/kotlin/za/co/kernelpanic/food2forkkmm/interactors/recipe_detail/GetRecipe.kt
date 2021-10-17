package za.co.kernelpanic.food2forkkmm.interactors.recipe_detail

import kotlinx.coroutines.flow.flow
import za.co.kernelpanic.food2forkkmm.datasource.cache.RecipeCache
import za.co.kernelpanic.food2forkkmm.domain.model.GenericMessageInfo
import za.co.kernelpanic.food2forkkmm.domain.model.Recipe
import za.co.kernelpanic.food2forkkmm.domain.model.UIComponentType
import za.co.kernelpanic.food2forkkmm.domain.util.CommonFlow
import za.co.kernelpanic.food2forkkmm.domain.util.DataState
import za.co.kernelpanic.food2forkkmm.domain.util.asCommonFlow

class GetRecipe(private val recipeCache: RecipeCache) {

    fun execute(recipeId: Int): CommonFlow<DataState<Recipe>> {
        return flow {
            emit(DataState.loading())
            try {
                val response = recipeCache.get(recipeId = recipeId)
                emit(DataState.data(response))
            } catch (e: Exception) {
                emit(DataState.error<Recipe>(errorMessage = GenericMessageInfo.Builder()
                    .id("GetRecipe.Error")
                    .title("Error")
                    .uiComponentType(UIComponentType.Dialog)
                    .description(e.message ?: "Unknown error")
                    .build()))
            }
        }.asCommonFlow()
    }
}