package com.realmatesoft.pexelsdemo.backend.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "photos")
data class Photo(
    @PrimaryKey val id: Int,
    val avg_color: String,
    val height: Int,
    val liked: Boolean,
    val photographer: String,
    val photographer_id: Int,
    val photographer_url: String,
    @TypeConverters(SrcTypeConverter::class)
    val src: Src,
    val url: String,
    val width: Int
)