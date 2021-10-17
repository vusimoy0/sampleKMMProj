package za.co.kernelpanic.food2forkkmm.android.presentation.navigation

sealed class Screen(val route: String) {
    object RecipeList : Screen(route = "recipeList")
    object RecipeDetail : Screen(route = "recipeDetail")
}