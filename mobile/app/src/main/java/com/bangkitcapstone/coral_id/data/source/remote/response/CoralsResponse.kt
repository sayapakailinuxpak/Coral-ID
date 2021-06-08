package com.bangkitcapstone.coral_id.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CoralsResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("full_name")
    val fullName: String,

    @field:SerializedName("full_name_abbreviation")
    val fullNameAbbreviation: String,

    @field:SerializedName("coral_genus")
    val coralGenus: String,

    @field:SerializedName("coral_family")
    val coralFamily: String,

    @field:SerializedName("discoverer")
    val discoverer: String,

    @field:SerializedName("year_discovered")
    val yearDiscovered: Int,

    @field:SerializedName("characteristic")
    val characteristic: String,

    @field:SerializedName("kind_of_look_alike")
    val kindOfLookAlike: String,

    @field:SerializedName("distribution")
    val distribution: String,

    @field:SerializedName("coral_type")
    val coralType: String,

    @field:SerializedName("image_path")
    val imagePath: String
)
