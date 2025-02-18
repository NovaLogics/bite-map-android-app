package novalogics.android.bitemap.dashboard.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import novalogics.android.bitemap.core.navigation.FeatureNavigationApi

interface DashboardNavigationApi : FeatureNavigationApi

class DashboardNavigationApiImpl : DashboardNavigationApi {
    override fun registerNavigationGraph(
        navController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        DashboardNavigationGraph.registerNavigationGraph(
            navController = navController,
            navGraphBuilder = navGraphBuilder
        )
    }
}
