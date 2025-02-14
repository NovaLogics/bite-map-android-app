package novalogics.android.bitemap.location.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import novalogics.android.bitemap.location.domain.repository.LocationRepository
import novalogics.android.bitemap.location.domain.room.LocationDao
import novalogics.android.bitemap.location.domain.usecase.FetchRestaurantDetailUseCase
import novalogics.android.bitemap.location.domain.usecase.GetAllPlacesFromDbUseCase
import novalogics.android.bitemap.location.domain.usecase.GetDirectionUseCase
import novalogics.android.bitemap.location.domain.usecase.GetLocationUpdateUseCase
import novalogics.android.bitemap.location.domain.usecase.InsertPlacesToDbUseCase
import novalogics.android.bitemap.location.domain.usecase.SearchRestaurantUseCase

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideSearchRestaurantUseCase(
        locationRepository: LocationRepository
    ): SearchRestaurantUseCase = SearchRestaurantUseCase(
        locationRepository = locationRepository
    )

    @Provides
    fun provideFetchRestaurantDetailUseCase(
        locationRepository: LocationRepository
    ): FetchRestaurantDetailUseCase = FetchRestaurantDetailUseCase(
        locationRepository = locationRepository
    )

    @Provides
    fun provideGetDirectionUseCase(
        locationRepository: LocationRepository
    ): GetDirectionUseCase = GetDirectionUseCase(
        locationRepository = locationRepository
    )

    @Provides
    fun provideGetLocationUpdateUseCase(
        locationRepository: LocationRepository
    ): GetLocationUpdateUseCase = GetLocationUpdateUseCase(
        locationRepository = locationRepository
    )

    @Provides
    fun provideGetAllPlacesFromDbUseCase(
        locationDao: LocationDao
    ): GetAllPlacesFromDbUseCase = GetAllPlacesFromDbUseCase(
        locationDao = locationDao
    )

    @Provides
    fun provideInsertPlacesToDbUseCase(
        locationDao: LocationDao
    ): InsertPlacesToDbUseCase = InsertPlacesToDbUseCase(
        locationDao = locationDao
    )

}
