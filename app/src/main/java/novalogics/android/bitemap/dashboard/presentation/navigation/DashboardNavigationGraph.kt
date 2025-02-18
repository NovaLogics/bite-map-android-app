package novalogics.android.bitemap.dashboard.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import novalogics.android.bitemap.core.navigation.DashboardRoute
import novalogics.android.bitemap.core.navigation.FeatureNavigationApi
import novalogics.android.bitemap.core.navigation.LocationRoute
import novalogics.android.bitemap.core.navigation.NavigationRoute
import novalogics.android.bitemap.dashboard.presentation.screens.home.HomeScreen
import novalogics.android.bitemap.dashboard.presentation.screens.permission.PermissionScreen

object DashboardNavigationGraph : FeatureNavigationApi {
    override fun registerNavigationGraph(
        navController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation(
            startDestination = DashboardRoute.PERMISSION_SCREEN.route,
            route = NavigationRoute.DASHBOARD.route
        ){
            composable(route = DashboardRoute.PERMISSION_SCREEN.route) {
                PermissionScreen(navController)
            }
            composable(route = DashboardRoute.HOME_SCREEN.route) {
                val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
                HomeScreen(navController){place ->
                    savedStateHandle?.set("place", place)
                    navController.navigate(LocationRoute.GOOGLE_MAPS.route)
                }
            }
        }
    }
}
