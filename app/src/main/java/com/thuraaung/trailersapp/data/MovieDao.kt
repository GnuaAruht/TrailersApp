package com.thuraaung.trailersapp.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thuraaung.trailersapp.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movie : List<Movie>)

    @Query("select * from movie order by releaseDate desc")
    fun getAllMovie() : PagingSource<Int, Movie>

    @Query("delete from movie")
    fun deleteAllMovie()

}