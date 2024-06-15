package com.marioioannou.showseeker.presentation.MovieList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marioioannou.showseeker.domain.repository.MovieListRepository
import com.marioioannou.showseeker.utils.Category
import com.marioioannou.showseeker.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository
): ViewModel() {

    private var _state = MutableStateFlow(MovieListState())
    val state = _state.asStateFlow()

    init{

        getPopularMovieList(false)
        getUpcomingMovieList(false)

    }

    fun onEvent(event: MovieListUIEvents){
        when(event){
            MovieListUIEvents.Navigate -> {
                _state.update {
                    it.copy(
                        isCurrentScreenPopular = !state.value.isCurrentScreenPopular
                    )
                }
            }
            is MovieListUIEvents.Paginate -> {
                if (event.category == Category.POPULAR){
                    getPopularMovieList(true)
                }else if (event.category == Category.UPCOMING){
                    getUpcomingMovieList(true)
                }
            }
        }
    }

    private fun getPopularMovieList(fetchRemoteData: Boolean){
        viewModelScope.launch{
            _state.update {
                it.copy(isLoading = true)
            }
            movieListRepository.getMovieList(
                fetchRemoteData,
                Category.POPULAR,
                state.value.popularMoviesPage
            ).collectLatest { result ->
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
                        result.data?.let {popularMovieList ->
                            _state.update {
                                it.copy(
                                    popularMovieList = state.value.popularMovieList + popularMovieList.shuffled(),
                                    popularMoviesPage = state.value.popularMoviesPage + 1
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getUpcomingMovieList(fetchRemoteData: Boolean){
        viewModelScope.launch{
            _state.update {
                it.copy(isLoading = true)
            }
            movieListRepository.getMovieList(
                fetchRemoteData,
                Category.UPCOMING,
                state.value.upcomingMoviesPage
            ).collectLatest { result ->
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
                        result.data?.let {upcomingMovieList ->
                            _state.update {
                                it.copy(
                                    upcomingMovieList = state.value.upcomingMovieList + upcomingMovieList.shuffled(),
                                    upcomingMoviesPage = state.value.upcomingMoviesPage + 1
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}