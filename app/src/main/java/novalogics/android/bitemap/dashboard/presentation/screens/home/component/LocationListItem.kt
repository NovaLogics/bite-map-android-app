package novalogics.android.bitemap.dashboard.presentation.screens.home.component

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.LatLng
import novalogics.android.bitemap.app.ui.theme.BiteMapTheme
import novalogics.android.bitemap.location.domain.model.PlaceDetails

@Composable
fun LocationListItem(it: PlaceDetails) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.medium
            )
            .padding(16.dp)
    ) {
        // Name
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Name",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Name: ${it.name}",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(4.dp))

        // Rating
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Rating",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Rating: ${it.rating}/5",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(4.dp))

        // Delivery Availability
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = if (it.delivery) Icons.Default.Check else Icons.Default.Close,
                contentDescription = "Delivery Availability",
                tint = if (it.delivery) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (it.delivery) "Delivery is available" else "Delivery is not available",
                color = if (it.delivery) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        // Start Location
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Start Location",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Start: ${it.origin.latitude}, ${it.origin.longitude}",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(4.dp))

        // Destination Location
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = "Destination Location",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Destination: ${it.destination.latitude}, ${it.destination.longitude}",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp
            )
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
fun LocationListItemPreview() {
    val samplePlaceDetails = PlaceDetails(
        placeId = "123",
        name = "Sample Place",
        destination = LatLng( 37.7749, -122.4194),
        origin = LatLng( 34.0522, -118.2437),
        delivery = true,
        rating = 4.5f
    )
    BiteMapTheme {
        LocationListItem(it = samplePlaceDetails)
    }
}
