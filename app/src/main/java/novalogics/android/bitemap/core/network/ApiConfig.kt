package novalogics.android.bitemap.core.network

import novalogics.android.bitemap.BuildConfig

object ApiConfig {
    const val BASE_URL = "https://maps.googleapis.com"
    const val DIRECTIONS_ENDPOINT = "/maps/api/directions/json"
    const val NEAR_BY_SEARCH_ENDPOINT = "/maps/api/place/nearbysearch/json"

    const val PLACES_API_KEY: String = BuildConfig.PLACES_API_KEY
}
