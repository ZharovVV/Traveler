package com.github.zharovvv.traveler.repository.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "place_table",
    foreignKeys = [
        ForeignKey(
            entity = CityEntity::class,
            parentColumns = ["id"],
            childColumns = ["city_id"],
            onDelete = CASCADE
        )
    ]
)
data class PlaceEntity(
    @PrimaryKey
    var id: Long,
    @ColumnInfo(name = "city_id")
    var cityId: Long,
    var name: String,
    var description: String,
    var coordinates: String,    //TODO Возможно придется изменить тип
    @ColumnInfo(name = "image_url")
    var imageUrl: String
)