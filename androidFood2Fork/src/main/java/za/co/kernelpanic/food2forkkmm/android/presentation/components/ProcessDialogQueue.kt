package za.co.kernelpanic.food2forkkmm.android.presentation.components

import androidx.compose.runtime.Composable
import za.co.kernelpanic.food2forkkmm.domain.model.GenericMessageInfo
import za.co.kernelpanic.food2forkkmm.domain.util.Queue

@Composable
fun ProcessDialogQueue(
    dialogQueue: Queue<GenericMessageInfo>?,
    onRemoveHeadMessageFromQueue: () -> Unit
) {
    dialogQueue?.peek()?.let { dialogInfo ->
        GenericDialog(
            onDismiss = dialogInfo.onDismiss,
            title = dialogInfo.title,
            description = dialogInfo.description,
            onRemoveHeadFromQueue = onRemoveHeadMessageFromQueue,
            positiveAction = dialogInfo.positiveAction,
            negativeAction = dialogInfo.negativeAction
        )
    }
}