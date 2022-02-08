package com.example.marvelapp.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Results")
data class Results (


  @PrimaryKey(autoGenerate = true)
  val characterId: Int,
  @SerializedName("id"          ) var id          : Int?            = null,
  @SerializedName("name"        ) var name        : String?         = null,
  @SerializedName("description" ) var description : String?         = null,
  @SerializedName("modified"    ) var modified    : String?         = null,
  @SerializedName("thumbnail"   )
  @Embedded
  var thumbnail   : Thumbnail?      = Thumbnail(),
  /*@SerializedName("resourceURI" ) var resourceURI : String?         = null,
  @SerializedName("comics"      ) var comics      : Comics?         = Comics(),
  @SerializedName("series"      ) var series      : Series?         = Series(),
  @SerializedName("stories"     ) var stories     : Stories?        = Stories(),
  @SerializedName("events"      ) var events      : Events?         = Events(),
  @SerializedName("urls"        ) var urls        : ArrayList<Urls> = arrayListOf()*/

)
{
  override fun toString(): String {
    return "Results(id=$id, name=$name, description=$description)"
  }
}

