package novalogics.android.bitemap.location.presentation.navigation

import novalogics.android.bitemap.common.navigation.FeatureApi

interface LocationFeatureApi : FeatureApi {

}

class LocationFeatureApiImpl : LocationFeatureApi {
    override fun registerGraph(
        navHostController: androidx.navigation.NavHostController,
        navGraphBuilder: androidx.navigation.NavGraphBuilder
    ) {
        InternalLocationFeatureApi.registerGraph(navHostController, navGraphBuilder)
    }

}
