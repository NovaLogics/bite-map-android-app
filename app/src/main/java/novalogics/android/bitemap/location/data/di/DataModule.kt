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
import novalogics.android.bitemap.location.data.repository.LocationRepositoryImpl
import novalogics.android.bitemap.location.domain.repository.LocationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @Singleton
    @Provides
    fun providePlacesClient(
        @ApplicationContext context: Context
    ): PlacesClient {
        Places.initialize(context, "")
        return Places.createClient(context)
    }

    @Singleton
    @Provides
    fun provideLocationRepository(
        fusedLocationProviderClient: FusedLocationProviderClient,
        placesClient: PlacesClient,
    ): LocationRepository = LocationRepositoryImpl(
        fusedLocationProviderClient = fusedLocationProviderClient,
        placesClient = placesClient,
    )

}
