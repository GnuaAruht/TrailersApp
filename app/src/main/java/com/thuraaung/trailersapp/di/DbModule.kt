package com.thuraaung.trailersapp.di

import android.content.Context
import androidx.room.Room
import com.thuraaung.trailersapp.data.MovieDao
import com.thuraaung.trailersapp.data.MovieDatabase
import com.thuraaung.trailersapp.data.RemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
class DbModule {

    @Provides
    fun provideMovieDb(@ApplicationContext context: Context): MovieDatabase {

        return Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java, "Movie.db"
        ).build()
    }


    @Provides
    fun provideMovieDao(database : MovieDatabase) : MovieDao {
        return database.getMovieDao()
    }

    @Provides
    fun provideRemoteKeyDao(database : MovieDatabase) : RemoteKeyDao {
        return database.getRemoteDao()
    }


}