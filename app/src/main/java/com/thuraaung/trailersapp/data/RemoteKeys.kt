package com.thuraaung.trailersapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key")
data class RemoteKeys(
    @PrimaryKey
    val movieId : Int,
    val prevKey : Int?,
    val nextKey : Int?
)