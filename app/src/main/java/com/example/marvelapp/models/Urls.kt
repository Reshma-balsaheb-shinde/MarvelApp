package com.example.marvelapp.models

import com.google.gson.annotations.SerializedName


data class Urls (

  @SerializedName("type" ) var type : String? = null,
  @SerializedName("url"  ) var url  : String? = null

)