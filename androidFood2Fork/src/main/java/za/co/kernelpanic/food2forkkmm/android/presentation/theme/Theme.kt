package za.co.kernelpanic.food2forkkmm.android.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import za.co.kernelpanic.food2forkkmm.android.presentation.components.CircularIndeterminateProgressBar
import za.co.kernelpanic.food2forkkmm.android.presentation.components.ProcessDialogQueue
import za.co.kernelpanic.food2forkkmm.domain.model.GenericMessageInfo
import za.co.kernelpanic.food2forkkmm.domain.util.Queue

private val LightThemeColors = lightColors(
    primary = Blue600,
    primaryVariant = Blue400,
    onPrimary = Black2,
    secondary = Color.White,
    secondaryVariant = Teal300,
    onSecondary = Color.Black,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = Grey1,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Black2,
)

val darkThemeColors = darkColors(

)

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun AppTheme(
    displayProgressBar: Boolean,
    dialogQueue: Queue<GenericMessageInfo>? = Queue(mutableListOf()),
    onRemoveHeadMessageFromQueue: () -> Unit,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = LightThemeColors,
        typography = QuickSandTypography,
        shapes = AppShapes
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Grey1)
        ) {
            ProcessDialogQueue(
                dialogQueue = dialogQueue,
                onRemoveHeadMessageFromQueue = onRemoveHeadMessageFromQueue
            )
            content()
            if (displayProgressBar) {
                CircularIndeterminateProgressBar(
                    isDisplayed = displayProgressBar,
                    verticalBias = 0.3f
                )
            }
        }
    }
}