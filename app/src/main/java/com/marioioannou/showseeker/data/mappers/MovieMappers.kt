package com.marioioannou.showseeker.data.mappers

import com.marioioannou.showseeker.data.local.database.MovieEntity
import com.marioioannou.showseeker.data.remote.respond.MovieDto
import com.marioioannou.showseeker.domain.model.Movie

fun MovieEntity.toMovie(
    category: String
):Movie {
    return Movie(
        id = id,
        category = category,
        adult = adult,
        backdrop_path = backdrop_path,
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count,
    )
}

fun MovieDto.toMovieEntity(
    category: String
):MovieEntity {
    return MovieEntity(
        id = id ?: -1,
        category = category,
        adult = adult ?: false,
        backdrop_path = backdrop_path ?: "",
        original_language = original_language ?: "",
        original_title = original_title ?: "",
        overview = overview ?: "",
        popularity = popularity ?: 0.0,
        poster_path = poster_path ?: "",
        release_date = release_date ?: "",
        title = title ?: "",
        video = video ?: false,
        vote_average = vote_average ?: 0.0,
        vote_count = vote_count ?: 0,
    )
}