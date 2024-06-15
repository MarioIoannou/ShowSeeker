package com.marioioannou.showseeker.navigation.tabs

import android.graphics.drawable.Drawable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
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
import com.marioioannou.showseeker.core.PopularScreen
import com.marioioannou.showseeker.core.UpcomingScreen

object UpcomingTab:Tab {
    private fun readResolve(): Any = UpcomingTab
    override val options: TabOptions

        @Composable
        get() {
            val title = "Upcoming"
            val icon = rememberVectorPainter(ImageVector.vectorResource(id = R.drawable.upcoming))

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(screen = UpcomingScreen){
            SlideTransition(navigator = it)
        }
    }
}