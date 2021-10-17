package za.co.kernelpanic.food2forkkmm.android.presentation.recipelist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import za.co.kernelpanic.food2forkkmm.android.presentation.components.RECIPE_IMAGE_HEIGHT
import za.co.kernelpanic.food2forkkmm.android.presentation.recipelist.components.LoadingRecipeListShimmer
import za.co.kernelpanic.food2forkkmm.android.presentation.recipelist.components.RecipeCard
import za.co.kernelpanic.food2forkkmm.datasource.network.RecipeServiceImpl.Companion.RECIPE_PAGINATION_PAGE_SIZE
import za.co.kernelpanic.food2forkkmm.domain.model.Recipe

@ExperimentalCoilApi
@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    page: Int,
    onTriggerNextPage: () -> Unit,
    onClickRecipeListItem: (Int) -> Unit
) {
    if (loading && recipes.isEmpty()) {
        LoadingRecipeListShimmer(imageHeight = RECIPE_IMAGE_HEIGHT.dp)
    } else if (recipes.isEmpty()) {

    } else {
        LazyColumn {
            itemsIndexed(items = recipes) { index, recipe ->
                if ((index + 1) >= (page * RECIPE_PAGINATION_PAGE_SIZE) && !loading) {
                    onTriggerNextPage()
                }
                RecipeCard(recipe = recipe, onClick = { onClickRecipeListItem(recipe.id) })
            }
        }
    }
}