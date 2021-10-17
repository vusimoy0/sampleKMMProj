package za.co.kernelpanic.food2forkkmm.domain.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CommonFlow<T>(private val origin: Flow<T>) : Flow<T> by origin {

    fun collectCommon(coroutineScope: CoroutineScope? = null, callback: (T) -> Unit) {
        onEach {
            callback(it)//communicates with swift ui
        }.launchIn(scope = coroutineScope?: CoroutineScope(Dispatchers.Main))
    }
}

fun <T>Flow<T>.asCommonFlow(): CommonFlow<T> = CommonFlow(origin = this)