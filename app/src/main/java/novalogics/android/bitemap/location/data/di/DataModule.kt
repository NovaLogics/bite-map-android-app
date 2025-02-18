package novalogics.android.bitemap.location.data.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import novalogics.android.bitemap.core.di.qualifier.LocationRetrofit
import novalogics.android.bitemap.core.network.ApiConfig
import novalogics.android.bitemap.location.data.datasource.network.LocationService
import novalogics.android.bitemap.location.data.repository.LocationRepositoryImpl
import novalogics.android.bitemap.location.domain.repository.LocationRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient
    = LocationServices.getFusedLocationProviderClient(context)

    @Singleton
    @Provides
    fun providePlacesClient(
        @ApplicationContext context: Context
    ): PlacesClient {
        Places.initialize(context, ApiConfig.PLACES_API_KEY)
        return Places.createClient(context)
    }

    @Singleton
    @Provides
    fun provideLocationRepository(
        fusedLocationProviderClient: FusedLocationProviderClient,
        placesClient: PlacesClient,
        locationService: LocationService
    ): LocationRepository = LocationRepositoryImpl(
        fusedLocationProviderClient = fusedLocationProviderClient,
        placesClient = placesClient,
        locationService = locationService,
    )

    @Provides
    fun provideLocationService(@LocationRetrofit retrofit: Retrofit): LocationService{
        return retrofit.create(LocationService::class.java)
    }
}

