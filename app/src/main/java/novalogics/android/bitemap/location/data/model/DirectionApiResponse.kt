package novalogics.android.bitemap.location.data.model

import com.google.gson.annotations.SerializedName


data class DirectionApiResponse (

  @SerializedName("geocoded_waypoints" ) var geocodedWaypoints : ArrayList<GeocodedWaypoints> = arrayListOf(),
  @SerializedName("routes"             ) var routes            : ArrayList<Routes>            = arrayListOf(),
  @SerializedName("status"             ) var status            : String?                      = null

)