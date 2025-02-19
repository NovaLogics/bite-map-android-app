package novalogics.android.bitemap.dashboard.data.repository

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import kotlinx.coroutines.suspendCancellableCoroutine
import novalogics.android.bitemap.core.network.ApiConfig
import novalogics.android.bitemap.core.network.MapsApiService
import novalogics.android.bitemap.dashboard.data.model.LocationMap
import novalogics.android.bitemap.dashboard.data.model.PlacesResponse
import novalogics.android.bitemap.dashboard.domain.repository.MapsRepository
import javax.inject.Inject
import kotlin.coroutines.resume

@SuppressLint("MissingPermission")
class MapsRepositoryImpl  @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val mapsApiService: MapsApiService
) : MapsRepository {

    private suspend fun getCurrentLocationOnce(): Location? = suspendCancellableCoroutine { continuation ->

        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 0)
            .setMaxUpdates(1)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                fusedLocationProviderClient.removeLocationUpdates(this)
                continuation.resume(locationResult.lastLocation)
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

        continuation.invokeOnCancellation {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }


    override suspend fun fetchNearRestaurants(radius: Int): PlacesResponse {
        return runCatching {

            val locationData = getCurrentLocationOnce()
                ?: throw IllegalStateException("Failed to retrieve location")

            val response = mapsApiService.getNearbyRestaurants(
                location = "${locationData.latitude},${locationData.longitude}",
                radius = radius,
                type = "restaurant",
                apiKey = ApiConfig.PLACES_API_KEY
            )
            response.currentLocationMap = LocationMap(
                locationData.latitude,
                locationData.longitude
            )
            response
        }.onFailure {
        }.getOrThrow()
    }
}
