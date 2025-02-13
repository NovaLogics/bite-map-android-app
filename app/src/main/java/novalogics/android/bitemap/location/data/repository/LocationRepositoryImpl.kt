package novalogics.android.bitemap.location.data.repository

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.LocationRestriction
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import novalogics.android.bitemap.common.navigation.events.PlacesResult
import novalogics.android.bitemap.location.domain.repository.LocationRepository
import javax.inject.Inject

@SuppressLint("MissingPermission")
class LocationRepositoryImpl @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val placesClient: PlacesClient,
) : LocationRepository {

    private var currentLocation: LatLng = LatLng(0.0, 0.0)

    private val token = AutocompleteSessionToken.newInstance()

    override fun getLocationOnce(location: (Location) -> Unit) {
        val locationResult =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 100)
                .setIntervalMillis(1000)
                .setMaxUpdates(1)
                .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.locations[0]?.let { locationData ->
                    currentLocation = LatLng(locationData.latitude, locationData.longitude)
                    location.invoke(locationData)
                }
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationResult, locationCallback, null)
    }

    override fun searchRestaurants(query: String): Flow<PlacesResult> = callbackFlow {

        getLocationOnce { location ->

            val locationRestriction = findLocationRestriction(location)

            val request = FindAutocompletePredictionsRequest.builder()
                .setSessionToken(token)
                .setCountries(mutableListOf("LK"))
                .setQuery(query)
                .setOrigin(LatLng(location.latitude, location.longitude))
                .setTypesFilter(mutableListOf("restaurant"))
                .setLocationRestriction(locationRestriction)
                .build()

            placesClient.findAutocompletePredictions(request)
                .addOnSuccessListener {
                    trySend(PlacesResult.Success(location, it.autocompletePredictions))
                }
                .addOnFailureListener {
                    trySend(PlacesResult.Error(it.message.toString()))
                }

        }
        awaitClose{}
    }

    private fun findLocationRestriction(location: Location): LocationRestriction {
        return RectangularBounds.newInstance(
            LatLng(location.latitude - 0.9, location.longitude - 0.9),
            LatLng(location.latitude + 0.9, location.longitude + 0.9),
        )
    }
}
