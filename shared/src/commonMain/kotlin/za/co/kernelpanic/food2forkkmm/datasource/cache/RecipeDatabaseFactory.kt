package za.co.kernelpanic.food2forkkmm.datasource.cache

import com.squareup.sqldelight.db.SqlDriver
import za.co.kernelpanic.datasource.cache.Recipe_Entity
import za.co.kernelpanic.food2forkkmm.domain.model.Recipe
import za.co.kernelpanic.food2forkkmm.domain.util.DateTimeUtil

class RecipeDatabaseFactory(
    private val driverFactory: DriverFactory
) {
    fun createDatabase(): RecipeDatabase {
        return RecipeDatabase(driver = driverFactory.createDriver())
    }
}

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun Recipe_Entity.toRecipe(): Recipe {
    val dateTimeUtil = DateTimeUtil()

    return Recipe(
        id = id.toInt(),
        title = title,
        publisher = publisher,
        featuredImage = featured_image,
        rating = rating.toInt(),
        sourceUrl = source_url,
        ingredients = ingredients.convertIngredientsToList(),
        dateAdded = dateTimeUtil.toLocalDate(date_added),
        dateUpdated = dateTimeUtil.toLocalDate(date_updated)
    )
}

fun List<Recipe_Entity>.toRecipeList(): List<Recipe> {
    return map { it.toRecipe() }
}

fun List<String>.convertIngredientListToString(): String {
    val ingredientsString = StringBuilder()
    for (ingredient in this) {
        ingredientsString.append("$ingredient,")
    }
    return ingredientsString.toString()
}

fun String.convertIngredientsToList(): List<String> {
    val list: ArrayList<String> = ArrayList()

    for(ingredient in split(",")) {
        list.add(ingredient)
    }
    return list
}
