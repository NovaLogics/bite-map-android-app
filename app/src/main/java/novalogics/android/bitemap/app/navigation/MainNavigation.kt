package novalogics.android.bitemap.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import novalogics.android.bitemap.common.navigation.NavigationRoute

@Composable
fun MainNavigation(
    navHostController: NavHostController,
    navigationProvider: NavigationProvider
) {
    NavHost(
        navController = navHostController,
        startDestination = NavigationRoute.DASHBOARD.route
    ) {
        navigationProvider.dashboardApi.registerNavigationGraph(
            navController = navHostController,
            navGraphBuilder = this
        )
        navigationProvider.locationFeatureApi.registerNavigationGraph(
            navController = navHostController,
            navGraphBuilder = this
        )
    }
}
