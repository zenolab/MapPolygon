package com.tgrodz.mappolygon.model

import com.google.gson.annotations.SerializedName


data class Area (
   @SerializedName("FieldNo") val fieldNo : String,
   @SerializedName("pol") val pol : String,
   @SerializedName("field_ID") val field_ID : Int,
   @SerializedName("FarmName") val farmName : String,
   @SerializedName("farm_ID") val farm_ID : Int,
   @SerializedName("CornType") val cornType : String,
   @SerializedName("FieldNoDescr") val fieldNoDescr : String,
   @SerializedName("Area") val area : Double,
   @SerializedName("min_X") val min_X : Double,
   @SerializedName("max_X") val max_X : Double,
   @SerializedName("min_Y") val min_Y : Double,
   @SerializedName("max_Y") val max_Y : Double,
   @SerializedName("CultList") val cultList : String
)
