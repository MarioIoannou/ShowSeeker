package com.marioioannou.showseeker.navigation.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import com.marioioannou.showseeker.R
import com.marioioannou.showseeker.presentation.MovieList.screens.PopularScreen

object PopularTab:Tab {
    private fun readResolve(): Any = PopularTab
    override val options: TabOptions

        @Composable
        get() {
            val title = "Popular"
            val icon = rememberVectorPainter(ImageVector.vectorResource(id = R.drawable.popular))

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(screen = PopularScreen){
            SlideTransition(navigator = it)
        }
    }
}