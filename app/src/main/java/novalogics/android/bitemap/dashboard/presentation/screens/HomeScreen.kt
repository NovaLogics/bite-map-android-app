package novalogics.android.bitemap.dashboard.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import novalogics.android.bitemap.core.navigation.LocationRoute
import novalogics.android.bitemap.location.domain.model.PlaceDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
){
    val list = viewModel.list.collectAsState(initial = emptyList())
    
    Scaffold (topBar = {
        TopAppBar(
            title = { Text(text = "Home Screen")},
            actions = {
                IconButton(onClick = {
                    navHostController.navigate(LocationRoute.PLACES_SEARCH.route)
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "")
                }
            }
        )
    }){
        Log.d("TAG", "${it}")

        if(list.value.isEmpty()){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center,
            ){
                Text(
                    text = "No destination found",
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(24.dp)
                )
            }
        }else{
            LazyColumn {
                items(list.value){
                    LocationListItem(it)
                }
            }

        }
        
    }
}

@Composable
fun LocationListItem(it: PlaceDetails){

    Column (modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
    ){
        Text(
            text = "Start: ${it.origin.latitude} , ${it.origin.longitude}",
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Destination: ${it.destination.latitude} , ${it.destination.longitude}",
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Name: ${it.name}",
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Rating: ${it.rating}/5",
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if(it.delivery) "Delivery is available" else "Delivery is Not available",
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

}
