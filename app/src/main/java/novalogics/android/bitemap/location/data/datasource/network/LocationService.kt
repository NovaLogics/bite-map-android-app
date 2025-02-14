package novalogics.android.bitemap.location.data.datasource.network

import novalogics.android.bitemap.location.data.model.DirectionApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationService {

    @GET("maps/api/directions/json")
    suspend fun getDirection(
        @Query("origin") origin:String,
        @Query("destination") destination:String,
        @Query("key") key:String
    ): DirectionApiResponse
}
