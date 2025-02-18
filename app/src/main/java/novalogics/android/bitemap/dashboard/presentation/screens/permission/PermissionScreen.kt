package novalogics.android.bitemap.dashboard.presentation.screens.permission

import android.Manifest
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import novalogics.android.bitemap.core.navigation.DashboardRoute


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionScreen(navController: NavHostController) {
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.values.all { it }) {
            navController.navigate(DashboardRoute.HOME_SCREEN.route)
        } else {
            finishAffinity(navController.context as ComponentActivity)
        }
    }

    LaunchedEffect(Unit) {
        if (permissionState.allPermissionsGranted) {
            navController.navigate(DashboardRoute.HOME_SCREEN.route)
        } else {
            launcher.launch(permissionState.permissions.map { it.permission }.toTypedArray())
           // permissionState.launchMultiplePermissionRequest()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Checking permissions...")
    }
}
