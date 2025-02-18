package novalogics.android.bitemap.dashboard.domain.repository


import novalogics.android.bitemap.dashboard.data.model.PlacesResponse

interface MapsRepository {
    suspend fun fetchNearRestaurants( radius: Int): PlacesResponse
}
