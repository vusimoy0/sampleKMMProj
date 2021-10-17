package za.co.kernelpanic.food2forkkmm.presentation.recipe_detail

import za.co.kernelpanic.food2forkkmm.domain.model.GenericMessageInfo
import za.co.kernelpanic.food2forkkmm.domain.model.Recipe
import za.co.kernelpanic.food2forkkmm.domain.util.Queue

actual data class RecipeDetailState(
    val isLoading: Boolean = false,
    val recipe: Recipe? = null,
    val queue: Queue<GenericMessageInfo> = Queue(mutableListOf())
) {
    constructor() : this(
        isLoading = false,
        recipe = null,
        queue = Queue(mutableListOf())
    )
}