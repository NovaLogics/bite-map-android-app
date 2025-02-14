package novalogics.android.bitemap.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import novalogics.android.bitemap.app.navigation.NavigationProvider
import novalogics.android.bitemap.dashboard.presentation.navigation.DashboardNavigationApi
import novalogics.android.bitemap.location.presentation.navigation.LocationNavigationApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNavigationProvider(
        dashboardApi: DashboardNavigationApi,
        locationFeatureApi: LocationNavigationApi
    ): NavigationProvider =
        NavigationProvider(
            dashboardApi = dashboardApi,
            locationFeatureApi = locationFeatureApi,
        )
}
