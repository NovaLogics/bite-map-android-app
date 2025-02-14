package novalogics.android.bitemap.dashboard.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import novalogics.android.bitemap.dashboard.presentation.navigation.DashboardApi
import novalogics.android.bitemap.dashboard.presentation.navigation.DashboardApiImpl

@Module
@InstallIn(SingletonComponent::class)
object UiModule {

    @Provides
    fun provideDashboardApi(): DashboardApi = DashboardApiImpl()
}
