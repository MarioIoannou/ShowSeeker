package com.marioioannou.showseeker.presentation.MovieList

import com.marioioannou.showseeker.domain.model.Movie

data class MovieListState(

    val isLoading: Boolean = false,

    val popularMoviesPage: Int = 1,
    val upcomingMoviesPage: Int = 1,

    val isCurrentScreenPopular: Boolean = true,

    val popularMovieList: List<Movie> = emptyList(),
    val upcomingMovieList: List<Movie> = emptyList()
)