package com.marioioannou.showseeker.core

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import com.marioioannou.showseeker.presentation.MovieList.MovieListState
import com.marioioannou.showseeker.presentation.MovieList.MovieListUIEvents
import com.marioioannou.showseeker.presentation.MovieList.MovieListViewModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailScreen(val movieId: String) : Screen, Parcelable {

    @Composable
    override fun Content() {
        val movieDetailViewModel: MovieListViewModel = hiltViewModel()
        val state by movieDetailViewModel.state.collectAsState()

        MovieDetailContent(
            state = state,
            onEvent = { event -> movieDetailViewModel.onEvent(event) }
        )
    }
}

@Composable
fun MovieDetailContent(
    state: MovieListState,
    onEvent: (MovieListUIEvents) -> Unit
) {

}