package novalogics.android.bitemap.dashboard.presentation.screens.permission

import android.Manifest
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import novalogics.android.bitemap.R
import novalogics.android.bitemap.app.ui.theme.BiteMapTheme
import novalogics.android.bitemap.core.navigation.DashboardRoute


@Composable
fun PermissionScreen(navController: NavHostController) {
    PermissionHandler(navController = navController)
    ScreenUiContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenUiContent(){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.displayMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        textAlign = TextAlign.Center,
                    )
                },
                modifier = Modifier
                    .height(56.dp)
                    .background(MaterialTheme.colorScheme.background)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Text(
                text = "Checking permissions...",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
               modifier =  Modifier.padding(12.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.bitemap_logo_main),
                contentDescription = "BiteMap Logo",
                modifier = Modifier
                    .size(180.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionHandler(
    navController: NavHostController,
    permissionList: List<String> = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
) {
    val permissionState = rememberMultiplePermissionsState(permissions = permissionList)

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
}

@Preview(
    name = "Light Mode",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PermissionScreenPreview() {
    BiteMapTheme {
        ScreenUiContent()
    }
}
