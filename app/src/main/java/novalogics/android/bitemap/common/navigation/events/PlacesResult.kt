package novalogics.android.bitemap.common.navigation.events

import android.location.Location
import com.google.android.libraries.places.api.model.AutocompletePrediction

sealed class PlacesResult(
    val location: Location? = null,
    val list: MutableList<AutocompletePrediction> = mutableListOf(),
    val message: String? = null
) {
    class Success(
        location: Location,
        list: MutableList<AutocompletePrediction>
    ) : PlacesResult(location, list)

    class Loading() : PlacesResult()

    class Idle() : PlacesResult()

    class Error(message: String) : PlacesResult(message = message)
}
