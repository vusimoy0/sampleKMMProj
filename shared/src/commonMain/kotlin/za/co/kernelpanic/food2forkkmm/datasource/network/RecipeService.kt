package za.co.kernelpanic.food2forkkmm.datasource.network

import za.co.kernelpanic.food2forkkmm.domain.model.Recipe

interface RecipeService {

    suspend fun search(
        page: Int,
        query: String,
    ): List<Recipe>

    suspend fun get(id: Int): Recipe
}