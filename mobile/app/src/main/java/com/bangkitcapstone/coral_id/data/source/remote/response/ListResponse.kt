package com.bangkitcapstone.coral_id.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListResponse<T>(
    @SerializedName("prediction_corals")
    val result: ArrayList<T>
)
