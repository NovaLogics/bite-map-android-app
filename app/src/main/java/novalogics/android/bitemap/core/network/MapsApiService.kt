package novalogics.android.bitemap.core.network

import novalogics.android.bitemap.dashboard.data.model.PlacesResponse
import novalogics.android.bitemap.location.data.model.DirectionApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MapsApiService {

    @GET(ApiConfig.DIRECTIONS_ENDPOINT)
    suspend fun getDirection(
        @Query("origin") origin:String,
        @Query("destination") destination:String,
        @Query("key") key:String
    ): DirectionApiResponse

    @GET(ApiConfig.NEAR_BY_SEARCH_ENDPOINT)
    suspend fun getNearbyRestaurants(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String = "restaurant",
        @Query("key") apiKey: String
    ): PlacesResponse
}
