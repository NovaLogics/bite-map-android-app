package novalogics.android.bitemap.location.domain.repository

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import novalogics.android.bitemap.core.navigation.events.LocationEvent
import novalogics.android.bitemap.core.navigation.events.PlacesResult
import novalogics.android.bitemap.location.domain.model.DirectionDetails
import novalogics.android.bitemap.location.domain.model.PlaceDetails

interface LocationRepository {

    fun getLocation(destination: LatLng): Flow<LocationEvent>

    fun getLocationOnce(location: (Location) -> Unit)

    fun searchRestaurants(query: String): Flow<PlacesResult>

    fun fetchPlace(placeId : String) : Flow<PlaceDetails>

    suspend fun getDirection(start: LatLng, destination:LatLng): DirectionDetails
}
