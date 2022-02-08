package com.example.marvelapp.models

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Data")
data class Data (

  @SerializedName("offset"  ) var offset  : Int?               = null,
  @SerializedName("limit"   ) var limit   : Int?               = null,
  @SerializedName("total"   ) var total   : Int?               = null,
  @SerializedName("count"   ) var count   : Int?               = null,
  @SerializedName("results" ) var results : ArrayList<Results> = arrayListOf()

)