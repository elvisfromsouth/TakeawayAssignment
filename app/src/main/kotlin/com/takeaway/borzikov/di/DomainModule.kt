package com.takeaway.borzikov.di

import com.takeaway.borzikov.domain.RestaurantInteractor
import com.takeaway.borzikov.domain.RestaurantInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindRestaurantInteractor(
        impl: RestaurantInteractorImpl
    ): RestaurantInteractor

}
