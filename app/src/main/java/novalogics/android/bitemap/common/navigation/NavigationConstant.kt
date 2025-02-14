package novalogics.android.bitemap.common.navigation

enum class NavigationRoutes(val route: String) {
    DASHBOARD("dashboard"),
    LOCATION("location")
}

enum class DashboardRoutes(val route: String){
    HOME_SCREEN("home")
}

enum class LocationRoutes(val route: String){
    PLACES_SEARCH("places"),
    GOOGLE_MAPS("google_maps")
}
