package com.github.zharovvv.traveler.repository.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "city_table"
)
data class CityEntity(
    @PrimaryKey
    var id: Long,
    var name: String,
    var location: String,
    var description: String,
    @ColumnInfo(name = "image_url")
    var imageUrl: String
)