package novalogics.android.bitemap.dashboard.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import novalogics.android.bitemap.dashboard.presentation.navigation.DashboardNavigationApi
import novalogics.android.bitemap.dashboard.presentation.navigation.DashboardNavigationApiImpl

@Module
@InstallIn(SingletonComponent::class)
object UiModule {

    @Provides
    fun provideDashboardApi(): DashboardNavigationApi = DashboardNavigationApiImpl()
}
