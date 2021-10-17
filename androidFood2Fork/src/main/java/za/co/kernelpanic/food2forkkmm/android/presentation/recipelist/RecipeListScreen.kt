package za.co.kernelpanic.food2forkkmm.android.presentation.recipelist

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import coil.annotation.ExperimentalCoilApi
import za.co.kernelpanic.food2forkkmm.android.presentation.recipelist.components.SearchAppBar
import za.co.kernelpanic.food2forkkmm.android.presentation.theme.AppTheme
import za.co.kernelpanic.food2forkkmm.presentation.recipe_list.FoodCategoryUtil
import za.co.kernelpanic.food2forkkmm.presentation.recipe_list.RecipeListEvents
import za.co.kernelpanic.food2forkkmm.presentation.recipe_list.RecipeListState

@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun RecipeListScreen(
    state: RecipeListState,
    onClickRecipeListItem: (Int) -> Unit,
    onTriggerEvent: (RecipeListEvents) -> Unit
) {
    AppTheme(displayProgressBar = state.isLoading, dialogQueue = state.queue, onRemoveHeadMessageFromQueue = {
        onTriggerEvent(RecipeListEvents.OnRemoveHeadMessageFromQueue)
    }) {
        val foodCategories = remember {
            FoodCategoryUtil().getAllFoodCategories()
        }

        Scaffold(topBar = {
            SearchAppBar(
                query = state.query,
                onQueryChange = {
                    onTriggerEvent(RecipeListEvents.OnUpdateQuery(it))
                },
                onExecuteSearch = {
                    onTriggerEvent(RecipeListEvents.NewSearch)
                },
                categories = foodCategories,
                onSelectedCategoryChanged = { onTriggerEvent(RecipeListEvents.OnSelectCategory(it))},
                selectedCategory = state.selectedCategory
            )
        }) {
            RecipeList(
                loading = state.isLoading,
                recipes = state.recipes,
                onClickRecipeListItem = onClickRecipeListItem,
                page = state.page,
                onTriggerNextPage = { onTriggerEvent(RecipeListEvents.NextPage) }
            )
        }
    }
}