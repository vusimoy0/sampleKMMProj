package za.co.kernelpanic.food2forkkmm.datasource.cache

import za.co.kernelpanic.datasource.cache.RecipedbQueries
import za.co.kernelpanic.food2forkkmm.datasource.network.RecipeServiceImpl.Companion.RECIPE_PAGINATION_PAGE_SIZE
import za.co.kernelpanic.food2forkkmm.domain.model.Recipe
import za.co.kernelpanic.food2forkkmm.domain.util.DateTimeUtil

class RecipeCacheImpl(
    private val database: RecipeDatabase,
    private val dateTimeUtil: DateTimeUtil
) : RecipeCache {

    private val queries: RecipedbQueries = database.recipedbQueries

    override fun insert(recipe: Recipe) {
        queries.insertRecipe(
            id = recipe.id.toLong(),
            title = recipe.title,
            publisher = recipe.publisher,
            featured_image = recipe.featuredImage,
            rating = recipe.rating.toLong(),
            source_url = recipe.sourceUrl,
            date_added = dateTimeUtil.toEpochMilliseconds(recipe.dateAdded),
            date_updated = dateTimeUtil.toEpochMilliseconds(recipe.dateUpdated),
            ingredients = recipe.ingredients.convertIngredientListToString()
        )
    }

    override fun insert(recipes: List<Recipe>) {
        for (recipe in recipes) {
            insert(recipe)
        }
    }

    override fun search(query: String, page: Int): List<Recipe> {
        return queries.searchRecipes(
            query = query,
            pageSize = RECIPE_PAGINATION_PAGE_SIZE.toLong(),
            offset = ((page - 1).toLong())
        ).executeAsList().toRecipeList()
    }

    override fun getAll(page: Int): List<Recipe> {
        return queries.getAllRecipes(
            pageSize = RECIPE_PAGINATION_PAGE_SIZE.toLong(),
            offset = ((page - 1).toLong())
        ).executeAsList().toRecipeList()
    }

    override fun get(recipeId: Int): Recipe? {
        return queries.getRecipeById(id = recipeId.toLong()).executeAsOneOrNull()?.toRecipe()
    }
}