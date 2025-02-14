package novalogics.android.bitemap.common.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface FeatureNavigationApi {
    fun registerNavigationGraph(navController: NavHostController, navGraphBuilder: NavGraphBuilder)
}
