package novalogics.android.bitemap.dashboard.data.model

import com.google.gson.annotations.SerializedName

data class PlacesResponse(
    @SerializedName("results")
    val results: List<Place> = emptyList()
)

data class Place(
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
    val location: Location? = null
)

data class Location(
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("lng")
    val lng: Double = 0.0,
)
