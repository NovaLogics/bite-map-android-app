package novalogics.android.bitemap.location.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import novalogics.android.bitemap.core.navigation.FeatureNavigationApi

interface LocationNavigationApi : FeatureNavigationApi

class LocationNavigationApiImpl : LocationNavigationApi {
    override fun registerNavigationGraph(
        navController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        LocationNavigationGraph.registerNavigationGraph(
            navController = navController,
            navGraphBuilder = navGraphBuilder
        )
    }
}
