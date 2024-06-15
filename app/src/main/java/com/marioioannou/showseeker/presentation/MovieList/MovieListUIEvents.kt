package com.marioioannou.showseeker.presentation.MovieList

sealed class MovieListUIEvents {

    data class Paginate(val category: String): MovieListUIEvents()
    object Navigate: MovieListUIEvents()

}