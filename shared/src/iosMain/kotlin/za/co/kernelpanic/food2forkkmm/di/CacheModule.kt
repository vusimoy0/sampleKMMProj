package za.co.kernelpanic.food2forkkmm.di

import za.co.kernelpanic.food2forkkmm.datasource.cache.*
import za.co.kernelpanic.food2forkkmm.domain.util.DateTimeUtil

class CacheModule {

    private val driverFactory: DriverFactory by lazy { DriverFactory() }
    val recipeDatabase: RecipeDatabase by lazy {
        RecipeDatabaseFactory(driverFactory = driverFactory).createDatabase()
    }

    val recipeCache: RecipeCache by lazy {
        RecipeCacheImpl(
            database = recipeDatabase,
            dateTimeUtil = DateTimeUtil()
        )
    }
}