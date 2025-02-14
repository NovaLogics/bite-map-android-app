package novalogics.android.bitemap.location.data.model

import com.google.gson.annotations.SerializedName


data class EndLocation (

  @SerializedName("lat" ) var lat : Double? = null,
  @SerializedName("lng" ) var lng : Double? = null

)