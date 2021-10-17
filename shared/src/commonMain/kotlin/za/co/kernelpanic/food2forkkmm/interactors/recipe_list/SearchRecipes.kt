package za.co.kernelpanic.food2forkkmm.interactors.recipe_list

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import za.co.kernelpanic.food2forkkmm.datasource.cache.RecipeCache
import za.co.kernelpanic.food2forkkmm.datasource.network.RecipeService
import za.co.kernelpanic.food2forkkmm.domain.model.GenericMessageInfo
import za.co.kernelpanic.food2forkkmm.domain.model.Recipe
import za.co.kernelpanic.food2forkkmm.domain.model.UIComponentType
import za.co.kernelpanic.food2forkkmm.domain.util.CommonFlow
import za.co.kernelpanic.food2forkkmm.domain.util.DataState
import za.co.kernelpanic.food2forkkmm.domain.util.asCommonFlow

class SearchRecipes(
    private val recipeService: RecipeService,
    private val recipeCache: RecipeCache
) {
    fun execute(page: Int, query: String): CommonFlow<DataState<List<Recipe>>> {
        return flow {
            emit(DataState.loading())
            try {
                val recipes = recipeService.search(page = page, query = query)

                delay(timeMillis = 500)
                if (query == "error") {
                    throw Exception("Forcing an error... Search Failed!")
                }

                recipeCache.insert(recipes)

                val cacheResult = if (query.isBlank()) {
                    recipeCache.getAll(page = page)
                } else {
                    recipeCache.search(query = query, page = page)
                }

                emit(DataState.data(data = cacheResult))
            } catch (e: Exception) {
                emit(
                    DataState.error<List<Recipe>>(
                        errorMessage = GenericMessageInfo.Builder()
                            .id("SearchRecipes.Error")
                            .title("Error")
                            .uiComponentType(UIComponentType.Dialog)
                            .description(e.message ?: "Unknown error")
                            .build()
                    )
                )
            }
        }.asCommonFlow()
    }
}