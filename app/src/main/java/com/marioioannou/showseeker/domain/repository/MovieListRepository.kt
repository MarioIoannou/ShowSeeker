package com.marioioannou.showseeker.domain.repository

import com.marioioannou.showseeker.domain.model.Movie
import com.marioioannou.showseeker.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {

    suspend fun getMovieList(
        fetchRemoteData: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>>

    suspend fun getMovie(id:Int): Flow<Resource<Movie>>

}