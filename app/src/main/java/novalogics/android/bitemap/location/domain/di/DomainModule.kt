package novalogics.android.bitemap.location.domain.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import novalogics.android.bitemap.location.domain.repository.LocationRepository
import novalogics.android.bitemap.location.domain.usecase.SearchRestaurantUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideSearchRestaurantUseCase(
        locationRepository: LocationRepository
    ): SearchRestaurantUseCase = SearchRestaurantUseCase(
        locationRepository = locationRepository
    )

}
