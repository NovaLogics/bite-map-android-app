package novalogics.android.bitemap.location.data.model

import com.google.gson.annotations.SerializedName


data class Steps (

  @SerializedName("distance"          ) var distance         : Distance?      = Distance(),
  @SerializedName("duration"          ) var duration         : Duration?      = Duration(),
  @SerializedName("end_location"      ) var endLocation      : EndLocation?   = EndLocation(),
  @SerializedName("html_instructions" ) var htmlInstructions : String?        = null,
  @SerializedName("polyline"          ) var polyline         : Polyline?      = Polyline(),
  @SerializedName("start_location"    ) var startLocation    : StartLocation? = StartLocation(),
  @SerializedName("travel_mode"       ) var travelMode       : String?        = null

)