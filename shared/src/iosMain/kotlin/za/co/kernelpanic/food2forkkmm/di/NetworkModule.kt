package za.co.kernelpanic.food2forkkmm.di

import za.co.kernelpanic.food2forkkmm.datasource.network.KtorClientFactory
import za.co.kernelpanic.food2forkkmm.datasource.network.RecipeService
import za.co.kernelpanic.food2forkkmm.datasource.network.RecipeServiceImpl

class NetworkModule {

    val recipeService: RecipeService by lazy {
        RecipeServiceImpl(httpClient = KtorClientFactory().build(),
        baseUrl = RecipeServiceImpl.BASE_URL)
    }
}