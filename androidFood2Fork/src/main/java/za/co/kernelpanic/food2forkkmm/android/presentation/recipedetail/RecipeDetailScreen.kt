package za.co.kernelpanic.food2forkkmm.android.presentation.recipedetail

import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import za.co.kernelpanic.food2forkkmm.android.presentation.components.RECIPE_IMAGE_HEIGHT
import za.co.kernelpanic.food2forkkmm.android.presentation.components.RecipeImage
import za.co.kernelpanic.food2forkkmm.android.presentation.recipedetail.composables.RecipeView
import za.co.kernelpanic.food2forkkmm.android.presentation.recipelist.components.LoadingRecipeListShimmer
import za.co.kernelpanic.food2forkkmm.android.presentation.recipelist.components.RecipeCard
import za.co.kernelpanic.food2forkkmm.android.presentation.theme.AppTheme
import za.co.kernelpanic.food2forkkmm.domain.model.Recipe
import za.co.kernelpanic.food2forkkmm.presentation.recipe_detail.RecipeDetailEvents
import za.co.kernelpanic.food2forkkmm.presentation.recipe_detail.RecipeDetailState
import za.co.kernelpanic.food2forkkmm.presentation.recipe_list.RecipeListEvents

@ExperimentalStdlibApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun RecipeDetailScreen(state: RecipeDetailState, onTriggerEvent: (RecipeDetailEvents) -> Unit) {
    AppTheme(displayProgressBar = state.isLoading, dialogQueue = state.queue,
        onRemoveHeadMessageFromQueue = {
            onTriggerEvent(
                RecipeDetailEvents.OnRemoveHeadMessageFromQueue
            )
        }) {

        if (state.recipe == null && state.isLoading) {
            LoadingRecipeListShimmer(imageHeight = RECIPE_IMAGE_HEIGHT.dp)
        } else if (state.recipe == null) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "We were unable to retrieve the details for this recipe. \n Try resetting the app",
                style = MaterialTheme.typography.body1
            )
        } else {
            RecipeView(recipe = state.recipe!!)
        }
    }
}