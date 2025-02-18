package novalogics.android.bitemap.location.data.mapper

import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.maps.android.PolyUtil
import novalogics.android.bitemap.location.data.model.DirectionApiResponse
import novalogics.android.bitemap.location.domain.model.DirectionDetails
import novalogics.android.bitemap.location.domain.model.PlaceDetails

fun Place.toDomain(placeId: String?) : PlaceDetails{
    return PlaceDetails(
        placeId = placeId.orEmpty(),
        name = this.name.orEmpty(),
        destination = this.latLng!!,
        origin = LatLng(0.0,0.0),
        delivery = this.delivery.equals(Place.BooleanPlaceAttributeValue.TRUE),
        rating = this.rating?.toFloat() ?: 0F
    )
}

fun DirectionApiResponse.toDomain(): DirectionDetails{
    return DirectionDetails(
        points = PolyUtil.decode(this.routes[0].overviewPolyline!!.points),
        distance = this.routes[0].legs[0].distance.toString(),
        duration = this.routes[0].legs[0].duration.toString(),
        html = this.routes[0].legs[0].steps[0].htmlInstructions!!,
    )
}
