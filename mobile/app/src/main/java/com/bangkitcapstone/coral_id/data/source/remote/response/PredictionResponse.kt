package com.bangkitcapstone.coral_id.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PredictionResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("full_name")
    val fullName: String,

    @field:SerializedName("image_path")
    val imagePath: String,

    @field:SerializedName("coral_type")
    val coralType: String

)
