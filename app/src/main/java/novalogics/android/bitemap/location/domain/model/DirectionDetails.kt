package novalogics.android.bitemap.location.domain.model

import com.google.android.gms.maps.model.LatLng

data class DirectionDetails(
    val points: MutableList<LatLng> = mutableListOf(),
    val distance: String="",
    val duration: String="",
    val html: String=""
)
