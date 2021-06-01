package com.bangkitcapstone.coral_id.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("corals")
	val corals: List<Int>,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)
