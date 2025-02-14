package novalogics.android.bitemap.common.navigation.events

import android.location.Location

sealed class LocationEvent(val location: Location? = null) {
    class LocationInProgress(location: Location) : LocationEvent(location)
    class ReachDestination() : LocationEvent()
    class Idle() : LocationEvent()
}
