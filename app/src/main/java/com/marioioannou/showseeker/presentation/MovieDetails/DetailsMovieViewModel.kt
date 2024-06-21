package com.marioioannou.showseeker.presentation.MovieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marioioannou.showseeker.domain.repository.MovieListRepository
import com.marioioannou.showseeker.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsMovieViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository
) : ViewModel(){

    private var _state = MutableStateFlow(MovieDetailsState())
    val state = _state.asStateFlow()

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
//            try {
//                val movie = movieListRepository.getMovie(movieId)
//                _state.value = MovieDetailsState(movie = movie)
//            } catch (e: Exception) {
//                _state.value = MovieDetailsState(error = e.localizedMessage)
//            }

            movieListRepository.getMovie(movieId).collectLatest { result ->
                when(result){
                    is Resource.Error -> {
                        _state.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let {movieDetails ->
                            _state.update {
                                it.copy(
                                    movie = movieDetails
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}