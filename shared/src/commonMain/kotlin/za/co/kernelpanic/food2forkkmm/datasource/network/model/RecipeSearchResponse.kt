package za.co.kernelpanic.food2forkkmm.datasource.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeSearchResponse(
    @SerialName("count")
    val count: Int,
    @SerialName("results")
    val results: List<RecipeDTO>
)