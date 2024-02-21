package com.girendi.flicknest.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre (
    @field:SerializedName("id")
    var id: Int = 0,
    @field:SerializedName("name")
    var name: String? = null
) : Parcelable