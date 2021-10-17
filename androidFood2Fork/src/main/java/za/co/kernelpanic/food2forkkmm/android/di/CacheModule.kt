package za.co.kernelpanic.food2forkkmm.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import za.co.kernelpanic.food2forkkmm.android.BaseApplication
import za.co.kernelpanic.food2forkkmm.datasource.cache.*
import za.co.kernelpanic.food2forkkmm.domain.util.DateTimeUtil
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CacheModule {

    @Singleton
    @Provides
    fun providesRecipeDatabase(context: BaseApplication): RecipeDatabase {
        return RecipeDatabaseFactory(driverFactory = DriverFactory(context = context)).createDatabase()
    }

    @Singleton
    @Provides
    fun providesRecipeCache(recipeDatabase: RecipeDatabase): RecipeCache {
        return RecipeCacheImpl(database = recipeDatabase, dateTimeUtil = DateTimeUtil())
    }
}