package novalogics.android.bitemap.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import novalogics.android.bitemap.common.navigation.NavigationRoutes

@Composable
fun MainNavigation(
    navHostController: NavHostController,
    navigationProvider: NavigationProvider
){
    NavHost(
        navController = navHostController,
        startDestination = NavigationRoutes.DASHBOARD.route){
        navigationProvider.dashboardApi.registerGraph(navHostController, this)
        navigationProvider.locationFeatureApi.registerGraph(navHostController, this)

    }
}
