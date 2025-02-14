package novalogics.android.bitemap.dashboard.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import novalogics.android.bitemap.common.navigation.FeatureApi

interface DashboardApi : FeatureApi {
}

class DashboardApiImpl : DashboardApi {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        InternalDashboardApi.registerGraph(navHostController, navGraphBuilder)
    }
}
