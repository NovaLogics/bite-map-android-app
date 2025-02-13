package novalogics.android.bitemap.location.domain.repository

import android.location.Location
import kotlinx.coroutines.flow.Flow
import novalogics.android.bitemap.common.navigation.events.PlacesResult

interface LocationRepository {
    fun getLocationOnce(location: (Location) -> Unit)

    fun searchRestaurants(query: String): Flow<PlacesResult>
}
