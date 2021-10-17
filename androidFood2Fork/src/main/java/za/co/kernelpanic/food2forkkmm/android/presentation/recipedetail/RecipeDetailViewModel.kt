package za.co.kernelpanic.food2forkkmm.android.presentation.recipedetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import za.co.kernelpanic.food2forkkmm.domain.model.GenericMessageInfo
import za.co.kernelpanic.food2forkkmm.domain.model.Recipe
import za.co.kernelpanic.food2forkkmm.domain.model.UIComponentType
import za.co.kernelpanic.food2forkkmm.domain.util.GenericMessageInfoQueueUtil
import za.co.kernelpanic.food2forkkmm.domain.util.Queue
import za.co.kernelpanic.food2forkkmm.interactors.recipe_detail.GetRecipe
import za.co.kernelpanic.food2forkkmm.presentation.recipe_detail.RecipeDetailEvents
import za.co.kernelpanic.food2forkkmm.presentation.recipe_detail.RecipeDetailState
import za.co.kernelpanic.food2forkkmm.presentation.recipe_list.RecipeListState
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRecipeUseCase: GetRecipe
) : ViewModel() {

    val state: MutableState<RecipeDetailState> = mutableStateOf(RecipeDetailState())

    init {
        try {
            savedStateHandle.get<Int>("recipeId")?.let { recipeId ->
                onTriggerEvent(RecipeDetailEvents.GetRecipe(recipeId = recipeId))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onTriggerEvent(event: RecipeDetailEvents) {
        when (event) {
            is RecipeDetailEvents.GetRecipe -> {
                getRecipe(recipeId = event.recipeId)
            }
            is RecipeDetailEvents.OnRemoveHeadMessageFromQueue -> {
                removeHeadMessage()
            }
            else -> {
                appendToMessageQueue(
                    GenericMessageInfo.Builder()
                        .id(UUID.randomUUID().toString())
                        .title("Error")
                        .uiComponentType(UIComponentType.Dialog)
                        .description("Invalid Event")
                )
            }
        }
    }

    private fun getRecipe(recipeId: Int) {
        getRecipeUseCase.execute(recipeId = recipeId).collectCommon(coroutineScope = viewModelScope) { dataState ->
            state.value = state.value.copy(isLoading = dataState.isLoading)

            dataState.data?.let { recipe ->
                state.value = state.value.copy(recipe = recipe)
            }

            dataState.message?.let { message ->
                appendToMessageQueue(
                    GenericMessageInfo.Builder()
                        .id(UUID.randomUUID().toString())
                        .title("Error")
                        .uiComponentType(UIComponentType.Dialog)
                        .description("Invalid Event")
                )
            }
        }
    }

    private fun appendToMessageQueue(messageInfo: GenericMessageInfo.Builder) {

        if (!GenericMessageInfoQueueUtil().doesMessageAlreadyExistInQueue(
                queue = state.value.queue, messageInfo = messageInfo.build()
            )
        ) {
            val queue = state.value.queue
            queue.add(messageInfo.build())
            state.value = state.value.copy(queue = queue)
        }
    }

    private fun removeHeadMessage() {
        try {
            val queue = state.value.queue
            queue.remove()
            state.value = state.value.copy(queue = Queue(mutableListOf()))
            state.value = state.value.copy(queue = queue)
        }catch (e: Exception) {
            //nothing to remove, queue is empty
        }
    }
}