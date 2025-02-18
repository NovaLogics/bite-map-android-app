package novalogics.android.bitemap.dashboard.data.di

import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import novalogics.android.bitemap.core.network.MapsApiService
import novalogics.android.bitemap.dashboard.data.repository.MapsRepositoryImpl
import novalogics.android.bitemap.dashboard.domain.repository.MapsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideMapsRepositoryImpl(
        fusedLocationProviderClient: FusedLocationProviderClient,
        mapsApiService: MapsApiService
    ): MapsRepository = MapsRepositoryImpl(
        fusedLocationProviderClient = fusedLocationProviderClient,
        mapsApiService = mapsApiService,
    )
}
