package za.co.kernelpanic.food2forkkmm.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.request.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import za.co.kernelpanic.food2forkkmm.android.presentation.navigation.Navigation
import za.co.kernelpanic.food2forkkmm.datasource.network.KtorClientFactory
import za.co.kernelpanic.food2forkkmm.datasource.network.RecipeServiceImpl
import za.co.kernelpanic.food2forkkmm.datasource.network.model.RecipeDTO
import za.co.kernelpanic.food2forkkmm.datasource.network.toRecipe
import za.co.kernelpanic.food2forkkmm.domain.util.DateTimeUtil

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }
    }
}
