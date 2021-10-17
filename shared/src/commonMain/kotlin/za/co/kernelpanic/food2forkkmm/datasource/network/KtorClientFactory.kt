package za.co.kernelpanic.food2forkkmm.datasource.network

import io.ktor.client.*
import za.co.kernelpanic.food2forkkmm.datasource.network.model.RecipeDTO
import za.co.kernelpanic.food2forkkmm.domain.model.Recipe
import za.co.kernelpanic.food2forkkmm.domain.util.DateTimeUtil

expect class KtorClientFactory() {
    fun build(): HttpClient
}

fun RecipeDTO.toRecipe(): Recipe {
    val dateTimeUtil = DateTimeUtil()
    return Recipe(
        id = primaryKey,
        title = title,
        publisher = publisher,
        featuredImage = featuredImage,
        rating = rating,
        sourceUrl = sourceUrl,
        ingredients = ingredients,
        dateAdded = dateTimeUtil.toLocalDate(dateAdded.toDouble()),
        dateUpdated = dateTimeUtil.toLocalDate(dateUpdated.toDouble())
    )
}

fun List<RecipeDTO>.toRecipeList(): List<Recipe> {
    return this.map { it.toRecipe() }
}