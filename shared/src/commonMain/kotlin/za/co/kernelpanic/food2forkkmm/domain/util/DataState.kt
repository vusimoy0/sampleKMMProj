package za.co.kernelpanic.food2forkkmm.domain.util

import za.co.kernelpanic.food2forkkmm.domain.model.GenericMessageInfo

data class DataState<T>(
    val message: GenericMessageInfo? = null,
    val data: T? = null,
    val isLoading: Boolean = false
) {
    companion object {
        fun <T> loading(): DataState<T> {
            return DataState(isLoading = true)
        }

        fun <T> error(errorMessage: GenericMessageInfo): DataState<T> {
            return DataState(message = errorMessage)
        }

        fun <T> data(data: T?): DataState<T> {
            return DataState(data = data)
        }
    }
}