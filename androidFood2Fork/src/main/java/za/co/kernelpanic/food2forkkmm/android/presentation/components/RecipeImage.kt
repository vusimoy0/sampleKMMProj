package za.co.kernelpanic.food2forkkmm.android.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter

const val RECIPE_IMAGE_HEIGHT = 260

@ExperimentalCoilApi
@Composable
fun RecipeImage(url: String, contentDescription: String) {
    val painter = rememberImagePainter(data = url, builder = { crossfade(true) })
    val imageLoadState = painter.state

    Box {
        Image(
            painter = painter, contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxWidth()
                .height(RECIPE_IMAGE_HEIGHT.dp),
            contentScale = ContentScale.Crop
        )
    }

    when(imageLoadState) {
        is ImagePainter.State.Success -> {
            //show image i guess
        }
        is ImagePainter.State.Error -> {
            //show error
        }
        is ImagePainter.State.Loading -> {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(RECIPE_IMAGE_HEIGHT.dp)) {
                //empty for white background
            }
        }
        ImagePainter.State.Empty -> {}

    }
}