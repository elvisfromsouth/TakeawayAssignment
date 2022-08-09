package com.takeaway.borzikov.di

import android.content.Context
import android.content.res.AssetManager
import com.google.gson.Gson
import com.takeaway.borzikov.data.DefaultRestaurantRepository
import com.takeaway.borzikov.data.LocalFileRestaurantsListDataSource
import com.takeaway.borzikov.data.RestaurantsListDataSource
import com.takeaway.borzikov.data.mappers.RestaurantModelMapper
import com.takeaway.borzikov.domain.RestaurantRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object DataModule {

    @Provides
    fun provideRestaurantsListDataSource(
        assetManager: AssetManager,
        gson: Gson,
    ): RestaurantsListDataSource {
        return LocalFileRestaurantsListDataSource(
            assetManager = assetManager,
            gson = gson,
            fileName = FILE_NAME
        )
    }

    @Provides
    fun provideRestaurantRepository(
        dataSource: RestaurantsListDataSource,
        restaurantModelMapper: RestaurantModelMapper
    ): RestaurantRepository {
        return DefaultRestaurantRepository(
            dataSource = dataSource,
            restaurantModelMapper = restaurantModelMapper,
        )
    }

    @Provides
    fun providesAssetManager(
        @ApplicationContext context: Context,
    ): AssetManager {
        return context.assets
    }

    @Provides
    fun provideGson(
    ): Gson {
        return Gson()
    }

    private const val FILE_NAME = "example.json"

}