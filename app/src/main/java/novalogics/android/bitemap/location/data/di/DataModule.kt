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
import novalogics.android.bitemap.common.util.PLACES_API_KEY
import novalogics.android.bitemap.location.data.datasource.network.LocationService
import novalogics.android.bitemap.location.data.repository.LocationRepositoryImpl
import novalogics.android.bitemap.location.domain.repository.LocationRepository
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private const val CACHE_SIZE = 10 * 1024 * 1024L // 10 MB

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
        Places.initialize(context, PLACES_API_KEY)
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

    @DirectionApiBaseUrl
    @Provides
    fun provideDirectionApiBaseUrl(): String = "https://maps.googleapis.com/"

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache =
        Cache(context.cacheDir, CACHE_SIZE)


    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .protocols(listOf(Protocol.HTTP_1_1))
            .addInterceptor(logger)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .cache(cache)
            .build()
    }

    @LocationRetrofit
    @Provides
    fun provideLocationRetrofit(
        @DirectionApiBaseUrl url:String,
        okHttpClient: OkHttpClient
    ): Retrofit{
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideLocationService(@LocationRetrofit retrofit: Retrofit): LocationService{
        return retrofit.create(LocationService::class.java)
    }

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DirectionApiBaseUrl

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocationRetrofit
