package com.bangkitcapstone.coral_id.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListResponse<T>(
    @SerializedName("Response")
    val result: List<T>
)
