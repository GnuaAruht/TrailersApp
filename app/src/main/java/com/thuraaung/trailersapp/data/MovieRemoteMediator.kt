package com.thuraaung.trailersapp.data

import androidx.paging.*
import androidx.room.withTransaction
import com.thuraaung.trailersapp.api.MovieService
import com.thuraaung.trailersapp.model.Movie
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator (
    private val movieService : MovieService,
    private val movieDatabase : MovieDatabase
) : RemoteMediator<Int,Movie>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {

        val page = when(loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: START_PAGE
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state) ?:
                    throw InvalidObjectException("Remote key and the prevKey should not be null")

                // If the previous key is null, then we can't request more data
                val prevKey = remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys == null || remoteKeys.nextKey == null) {
                    throw InvalidObjectException("Movie key should not be null for $loadType")
                }
                remoteKeys.nextKey
            }
        }

        try {

            val apiResponse = movieService.getMovieList(page)
            val movies = apiResponse.results
            val endOfPaginationReached = movies.isEmpty()

            movieDatabase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    movieDatabase.getMovieDao().deleteAllMovie()
                    movieDatabase.getRemoteDao().clearRemoteKeys()
                }

                val prevKey = if (page == START_PAGE) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = movies.map {
                    RemoteKeys(movieId = it.id,prevKey = prevKey,nextKey = nextKey)
                }

                movieDatabase.getRemoteDao().insertAll(keys)
                movieDatabase.getMovieDao().insertAll(movies)

            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        }catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Movie>): RemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                movieDatabase.getRemoteDao().remoteKeyMovieId(movieId = movie.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Movie>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                movieDatabase.getRemoteDao().remoteKeyMovieId(movieId = movie.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Movie>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { movieId ->
                movieDatabase.getRemoteDao().remoteKeyMovieId(movieId)
            }
        }
    }


}