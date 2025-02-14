package novalogics.android.bitemap.dashboard.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import novalogics.android.bitemap.common.navigation.DashboardRoute
import novalogics.android.bitemap.common.navigation.FeatureApi
import novalogics.android.bitemap.common.navigation.NavigationRoute
import novalogics.android.bitemap.dashboard.presentation.screens.HomeScreen

object InternalDashboardApi : FeatureApi {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation(
            startDestination = DashboardRoute.HOME_SCREEN.route,
            route = NavigationRoute.DASHBOARD.route
        ){
            composable(route = DashboardRoute.HOME_SCREEN.route) {
                HomeScreen(navHostController)
            }

        }
    }
}
