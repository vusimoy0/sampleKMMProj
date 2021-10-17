package za.co.kernelpanic.food2forkkmm.android.presentation.recipelist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import za.co.kernelpanic.food2forkkmm.domain.model.*
import za.co.kernelpanic.food2forkkmm.domain.util.GenericMessageInfoQueueUtil
import za.co.kernelpanic.food2forkkmm.domain.util.Queue
import za.co.kernelpanic.food2forkkmm.interactors.recipe_list.SearchRecipes
import za.co.kernelpanic.food2forkkmm.presentation.recipe_list.FoodCategory
import za.co.kernelpanic.food2forkkmm.presentation.recipe_list.RecipeListEvents
import za.co.kernelpanic.food2forkkmm.presentation.recipe_list.RecipeListState
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val searchRecipes: SearchRecipes
) : ViewModel() {

    val state: MutableState<RecipeListState> = mutableStateOf(RecipeListState())

    init {
        onTriggerEvent(RecipeListEvents.LoadRecipes)

        //test custom dialog
//        val messageInfoBuilder = GenericMessageInfo.Builder()
//            .id(UUID.randomUUID().toString())
//            .title("Weird")
//            .uiComponentType(UIComponentType.Dialog)
//            .description("I don't know whats happening")
//            .positive(PositiveAction(positiveBtnTxt = "Cool", onPositiveAction = {
//                state.value = state.value.copy(query = "Kale")
//                onTriggerEvent(RecipeListEvents.NewSearch)
//            }))
//            .negative(NegativeAction(negativeBtnTxt = "Cancel",
//            onNegativeAction = {
//                state.value = state.value.copy(query = "Cookies")
//                onTriggerEvent(RecipeListEvents.NewSearch)
//            }))
//        appendToMessageQueue(messageInfo = messageInfoBuilder)
    }

    fun onTriggerEvent(event: RecipeListEvents) {
        when (event) {
            RecipeListEvents.LoadRecipes -> {
                loadRecipes()
            }
            RecipeListEvents.NextPage -> {
                nextPage()
            }
            RecipeListEvents.HandleError -> {
                GenericMessageInfo.Builder()
                    .id(UUID.randomUUID().toString())
                    .title("Error")
                    .uiComponentType(UIComponentType.Dialog)
                    .description("Unknown error")
                    .build()
            }
            RecipeListEvents.NewSearch -> {
                newSearch()
            }
            is RecipeListEvents.OnUpdateQuery -> {
                state.value = state.value.copy(query = event.query)
            }

            is RecipeListEvents.OnRemoveHeadMessageFromQueue -> {
                removeHeadMessage()
            }

            is RecipeListEvents.OnSelectCategory -> {
                onSelectCategory(event.category)
            }
        }
    }

    private fun onSelectCategory(category: FoodCategory) {
        state.value = state.value.copy(selectedCategory = category, query = category.value)
        newSearch()
    }

    private fun newSearch() {
        state.value = state.value.copy(page = 1, recipes = listOf())
        loadRecipes()
    }

    private fun nextPage() {
        state.value = state.value.copy(
            page = state.value.page +
                    1
        )
        loadRecipes()
    }

    private fun loadRecipes() {
        searchRecipes.execute(page = state.value.page, query = state.value.query)
            .collectCommon(coroutineScope = viewModelScope) { dataState ->

                state.value = state.value.copy(isLoading = dataState.isLoading)

                dataState.data?.let { recipes ->
                    appendRecipes(recipes = recipes)
                }

                dataState.message?.let {
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

    private fun appendRecipes(recipes: List<Recipe>) {
        val current = ArrayList(state.value.recipes)
        current.addAll(recipes)

        state.value = state.value.copy(recipes = current)
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