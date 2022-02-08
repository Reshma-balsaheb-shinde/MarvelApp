package com.example.marvelapp.models

import com.google.gson.annotations.SerializedName


data class CharacterList (

  @SerializedName("code"            ) var code            : Int?    = null,
  @SerializedName("status"          ) var status          : String? = null,
  @SerializedName("copyright"       ) var copyright       : String? = null,
  @SerializedName("attributionText" ) var attributionText : String? = null,
  @SerializedName("attributionHTML" ) var attributionHTML : String? = null,
  @SerializedName("etag"            ) var etag            : String? = null,
  @SerializedName("data"            ) var data            : Data?   = Data()

)