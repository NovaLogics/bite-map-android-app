package novalogics.android.bitemap.dashboard.data.mapper

import com.google.android.gms.maps.model.LatLng
import novalogics.android.bitemap.dashboard.data.model.PlaceMap
import novalogics.android.bitemap.location.domain.model.PlaceDetails

fun PlaceMap.toDetails(
    placeId: String = "",
    origin: LatLng,
    delivery: Boolean = false
): PlaceDetails {
    return PlaceDetails(
        placeId = placeId,
        name = this.name,
        destination = LatLng(
            this.geometry?.location?.latitude ?: 0.0,
            this.geometry?.location?.longitude ?: 0.0
        ),
        origin = origin,
        delivery = delivery,
        rating = this.rating.toFloat() ?: 0F
    )
}
