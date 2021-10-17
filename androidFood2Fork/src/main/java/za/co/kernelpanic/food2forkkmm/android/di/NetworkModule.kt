package za.co.kernelpanic.food2forkkmm.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import za.co.kernelpanic.food2forkkmm.datasource.network.KtorClientFactory
import za.co.kernelpanic.food2forkkmm.datasource.network.RecipeService
import za.co.kernelpanic.food2forkkmm.datasource.network.RecipeServiceImpl
import za.co.kernelpanic.food2forkkmm.datasource.network.RecipeServiceImpl.Companion.BASE_URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesHttpClient(): HttpClient {
        return KtorClientFactory().build()
    }

    @Singleton
    @Provides
    fun providesRecipeService(httpClient: HttpClient): RecipeService {
        return RecipeServiceImpl(httpClient = httpClient, baseUrl = BASE_URL)
    }
}