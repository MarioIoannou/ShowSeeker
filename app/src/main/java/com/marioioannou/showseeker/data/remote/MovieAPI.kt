package com.marioioannou.showseeker.data.remote

import com.marioioannou.showseeker.data.remote.respond.MovieListDto
import com.marioioannou.showseeker.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {

    @GET("/3/movie/{category}")
    suspend fun getMovieList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): MovieListDto

}