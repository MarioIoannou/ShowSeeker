package com.marioioannou.showseeker.data.repository

import com.marioioannou.showseeker.data.local.database.MovieDatabase
import com.marioioannou.showseeker.data.mappers.toMovie
import com.marioioannou.showseeker.data.mappers.toMovieEntity
import com.marioioannou.showseeker.data.remote.MovieAPI
import com.marioioannou.showseeker.domain.model.Movie
import com.marioioannou.showseeker.domain.repository.MovieListRepository
import com.marioioannou.showseeker.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor (
    private val movieAPI: MovieAPI,
    private val movieDatabase: MovieDatabase
):MovieListRepository {

    override suspend fun getMovieList(
        fetchRemoteData: Boolean,
        category: String,
        page: Int,
    ): Flow<Resource<List<Movie>>> {
        return flow {

            emit(Resource.Loading(true))

            // Get movies from DB
            val localMovieList = movieDatabase.movieDao.getMovieListByCategory(category)

            // Is DB empty?
            val loadLocalMovieList = localMovieList.isNotEmpty() && !fetchRemoteData

            if (loadLocalMovieList){
                emit(Resource.Success(
                    data = localMovieList.map {movieEntity ->
                        movieEntity.toMovie(category)
                    }
                ))

                emit(Resource.Loading(false))

                return@flow
            }

            // If above is FALSE then fetch data from the API
            val remoteApiMovieList = try {

                movieAPI.getMovieList(category, page)

            }catch (e: IOException){

                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies - IOException"))
                return@flow

            }catch (e: HttpException){

                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies - HttpException"))
                return@flow

            }catch (e: Exception){

                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies - Exception"))
                return@flow

            }

            // Map remote data with entity
            val movieEntities = remoteApiMovieList.results.let {movieListDto ->
                movieListDto.map { movieDto ->
                    movieDto.toMovieEntity(category)
                }
            }

            // Store data locally
            movieDatabase.movieDao.updateOrInsertMovieList(movieEntities)

            emit(Resource.Success(
                movieEntities.map {movieEntity ->
                    movieEntity.toMovie(category)
                }
            ))

            emit(Resource.Loading(false))

        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {

        return flow {

            emit(Resource.Loading(true))

            val movieEntity = movieDatabase.movieDao.getMovieById(id)

            emit(Resource.Success(
                data = movieEntity.toMovie(movieEntity.category)
            ))

            emit(Resource.Loading(false))

        }

    }
}