package com.marioioannou.showseeker.presentation.MovieDetails

import com.marioioannou.showseeker.domain.model.Movie

data class MovieDetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
) {
}