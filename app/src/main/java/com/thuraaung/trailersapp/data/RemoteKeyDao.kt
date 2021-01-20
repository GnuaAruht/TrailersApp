package com.thuraaung.trailersapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey : List<RemoteKeys>)

    @Query("select * from remote_key where movieId = :movieId ")
    suspend fun remoteKeyMovieId(movieId : Int) : RemoteKeys?

    @Query("delete from remote_key")
    suspend fun clearRemoteKeys()
}