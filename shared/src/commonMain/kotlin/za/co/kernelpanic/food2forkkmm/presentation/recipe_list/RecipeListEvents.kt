package za.co.kernelpanic.food2forkkmm.presentation.recipe_list

sealed class RecipeListEvents {

    object LoadRecipes : RecipeListEvents()

    object NextPage : RecipeListEvents()

    object HandleError : RecipeListEvents()

    object NewSearch : RecipeListEvents()

    data class OnUpdateQuery(val query: String) : RecipeListEvents()

    data class OnSelectCategory(val category: FoodCategory) : RecipeListEvents()

    object OnRemoveHeadMessageFromQueue : RecipeListEvents()
}