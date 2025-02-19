package novalogics.android.bitemap.dashboard.data.model

import com.google.gson.annotations.SerializedName

data class PlacesResponse(
    @SerializedName("results")
    val results: List<PlaceMap> = emptyList(),
    @SerializedName("current_location")
    var currentLocationMap: LocationMap? = null,
)

data class PlaceMap(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("vicinity")
    val vicinity: String = "",
    @SerializedName("rating")
    val rating: Double = 0.0,
    @SerializedName("geometry")
    val geometry: Geometry? = null,
)

data class Geometry(
    @SerializedName("location")
    val location: LocationMap? = null
)

data class LocationMap(
    @SerializedName("lat")
    val latitude: Double = 0.0,
    @SerializedName("lng")
    val longitude: Double = 0.0,
)
