package novalogics.android.bitemap.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import novalogics.android.bitemap.app.navigation.MainNavigation
import novalogics.android.bitemap.app.navigation.NavigationProvider
import novalogics.android.bitemap.app.ui.theme.BiteMapTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationProvider: NavigationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BiteMapTheme {
                AppContent(navigationProvider = navigationProvider)
            }
        }
    }
}

@Composable
fun AppContent(navigationProvider: NavigationProvider) {
    val navHostController = rememberNavController()
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        MainNavigation(
            navHostController = navHostController,
            navigationProvider = navigationProvider
        )
    }
}
