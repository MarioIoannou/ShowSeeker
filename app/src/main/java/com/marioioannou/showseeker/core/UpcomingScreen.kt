package com.marioioannou.showseeker.core

import android.os.Parcelable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.marioioannou.showseeker.presentation.MovieList.MovieListState
import com.marioioannou.showseeker.presentation.MovieList.MovieListUIEvents
import com.marioioannou.showseeker.presentation.MovieList.MovieListViewModel
import com.marioioannou.showseeker.presentation.MovieList.components.PopularScreenComponent.MovieItem
import com.marioioannou.showseeker.utils.Category
import kotlinx.parcelize.Parcelize

@Parcelize
object UpcomingScreen: Screen,Parcelable {
    private fun readResolve(): Any = UpcomingScreen

    @Composable
    override fun Content() {
        val movieListViewModel: MovieListViewModel = hiltViewModel()
        val state by movieListViewModel.state.collectAsState()

        UpcomingMovieListContent(
            state = state,
            onEvent = { event -> movieListViewModel.onEvent(event) },
            navigator = LocalNavigator.current
        )
    }
}

@Composable
fun UpcomingMovieListContent(
    state: MovieListState,
    onEvent: (MovieListUIEvents) -> Unit,
    navigator: Navigator?
) {

    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {

        val lazyVerticalGrid = rememberLazyGridState()

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp),
            state = lazyVerticalGrid
        ){

            items(state.upcomingMovieList.size) { index ->
                MovieItem(movie = state.upcomingMovieList[index]) {selectedMovie ->
                    navigator?.push(MovieDetailScreen(movieId = selectedMovie.id.toString()))
                }

                if (index >= state.upcomingMovieList.size -1 && !state.isLoading){
                    onEvent(MovieListUIEvents.Paginate(Category.UPCOMING))
                }
            }

        }

    }
}