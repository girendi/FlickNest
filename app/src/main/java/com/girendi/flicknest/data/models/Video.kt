package com.girendi.flicknest.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Video(
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("key")
    val key: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("site")
    val site: String,
    @field:SerializedName("type")
    val type: String
) : Parcelable
