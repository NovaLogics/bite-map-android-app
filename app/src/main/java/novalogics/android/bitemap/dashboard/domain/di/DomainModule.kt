package novalogics.android.bitemap.dashboard.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import novalogics.android.bitemap.core.network.MapsApiService
import novalogics.android.bitemap.dashboard.domain.repository.MapsRepository
import novalogics.android.bitemap.dashboard.domain.usecase.GetNearByPlacesUseCase

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    fun provideGetNearByPlacesUseCase(
        mapsRepository: MapsRepository
    ): GetNearByPlacesUseCase = GetNearByPlacesUseCase(
        mapsRepository = mapsRepository
    )
}
