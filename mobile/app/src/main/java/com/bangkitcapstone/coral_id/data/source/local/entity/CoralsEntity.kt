package com.bangkitcapstone.coral_id.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "corals_entities")
@Parcelize
data class CoralsEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "full_name")
    val fullName: String,

    @ColumnInfo(name = "full_name_abbreviation")
    val fullNameAbbreviation: String,

    @ColumnInfo(name = "coral_genus")
    val coralGenus: String,

    @ColumnInfo(name = "coral_family")
    val coralFamily: String,

    @ColumnInfo(name = "discoverer")
    val discoverer: String,

    @ColumnInfo(name = "year_discovered")
    val yearDiscovered: Int,

    @ColumnInfo(name = "characteristic")
    val characteristic: String,

    @ColumnInfo(name = "kind_of_look_alike")
    val kindOfLookAlike: String,

    @ColumnInfo(name = "distribution")
    val distribution: String,

    @ColumnInfo(name = "coral_type")
    val coralType: String,

    @ColumnInfo(name = "image_path")
    val imagePath: String
) : Parcelable
