package za.co.kernelpanic.food2forkkmm.android.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import za.co.kernelpanic.food2forkkmm.android.presentation.recipedetail.RecipeDetailScreen
import za.co.kernelpanic.food2forkkmm.android.presentation.recipedetail.RecipeDetailViewModel
import za.co.kernelpanic.food2forkkmm.android.presentation.recipelist.RecipeListScreen
import za.co.kernelpanic.food2forkkmm.android.presentation.recipelist.RecipeListViewModel

@ExperimentalStdlibApi
@ExperimentalCoilApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.RecipeList.route) {

        composable(route = Screen.RecipeList.route) { navBackStackEntry ->

            val factory =
                HiltViewModelFactory(LocalContext.current, navBackStackEntry = navBackStackEntry)
            val viewModel: RecipeListViewModel =
                viewModel(key = RecipeListViewModel::class.java.name, factory = factory)

            RecipeListScreen(
                state = viewModel.state.value,
                onClickRecipeListItem = { recipeId ->
                    navController.navigate(route = "${Screen.RecipeDetail.route}/$recipeId")
                },
                onTriggerEvent = viewModel::onTriggerEvent
            )
        }

        composable(
            route = Screen.RecipeDetail.route + "/{recipeId}",
            arguments = listOf(navArgument(name = "recipeId") {
                type = NavType.IntType
            })

        ) { navBackStackEntry ->
            val factory =
                HiltViewModelFactory(LocalContext.current, navBackStackEntry = navBackStackEntry)
            val viewModel: RecipeDetailViewModel =
                viewModel(key = RecipeDetailViewModel::class.java.name, factory = factory)

            RecipeDetailScreen(state = viewModel.state.value, onTriggerEvent = viewModel::onTriggerEvent)
        }
    }
}