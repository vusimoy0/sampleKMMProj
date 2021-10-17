package za.co.kernelpanic.food2forkkmm.domain.util

import za.co.kernelpanic.food2forkkmm.domain.model.GenericMessageInfo

class GenericMessageInfoQueueUtil {

    fun doesMessageAlreadyExistInQueue(
        queue: Queue<GenericMessageInfo>,
        messageInfo: GenericMessageInfo
    ): Boolean {
        for (item in queue.items) {
            if (item.id == messageInfo.id) {
                return true
            }
        }
        return false
    }
}