package novalogics.android.bitemap.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface FeatureNavigationApi {
    fun registerNavigationGraph(navController: NavHostController, navGraphBuilder: NavGraphBuilder)
}
