package com.takeaway.borzikov.di

import android.content.Context
import android.content.SharedPreferences
import com.takeaway.borzikov.*
import com.takeaway.borzikov.common.AppDispatchers
import com.takeaway.borzikov.common.DefaultAppDispatchers
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module(
    includes = [
        PresentationModule.DependenciesModule::class
    ]
)
@InstallIn(ViewModelComponent::class)
object PresentationModule {

    @Provides
    fun providePreferencesStore(
        @ApplicationContext context: Context,
    ): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideAppDispatchers(
    ): AppDispatchers {
        return DefaultAppDispatchers()
    }


    @Module
    @InstallIn(ViewModelComponent::class)
    abstract class DependenciesModule {

        @Binds
        abstract fun bindRestaurantListStateHandler(
            impl: RestaurantListStateHandlerImpl
        ): RestaurantListStateHandler

    }

    private const val PREFERENCES_FILE_NAME = "user_preferences"

}