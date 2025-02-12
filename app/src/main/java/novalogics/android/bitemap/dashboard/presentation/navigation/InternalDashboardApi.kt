package novalogics.android.bitemap.dashboard.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import novalogics.android.bitemap.common.navigation.DashboardRoutes
import novalogics.android.bitemap.common.navigation.FeatureApi
import novalogics.android.bitemap.common.navigation.NavigationRoutes
import novalogics.android.bitemap.dashboard.presentation.screens.HomeScreen

object InternalDashboardApi : novalogics.android.bitemap.common.navigation.FeatureApi {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation(
            startDestination = novalogics.android.bitemap.common.navigation.DashboardRoutes.HOME_SCREEN.route,
            route = novalogics.android.bitemap.common.navigation.NavigationRoutes.DASHBOARD.route
        ){

            composable(route = novalogics.android.bitemap.common.navigation.DashboardRoutes.HOME_SCREEN.route) {
                HomeScreen()
            }

        }
    }
}
