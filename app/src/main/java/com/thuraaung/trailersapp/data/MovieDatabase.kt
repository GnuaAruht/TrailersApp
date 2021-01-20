package com.thuraaung.trailersapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thuraaung.trailersapp.model.Movie

@Database(
    entities = [Movie::class,RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getMovieDao() : MovieDao
    abstract fun getRemoteDao() : RemoteKeyDao

}