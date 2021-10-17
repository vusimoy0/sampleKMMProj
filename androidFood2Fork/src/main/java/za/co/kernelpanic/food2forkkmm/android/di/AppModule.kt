package za.co.kernelpanic.food2forkkmm.android.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import za.co.kernelpanic.food2forkkmm.android.BaseApplication
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }
}