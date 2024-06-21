package com.marioioannou.showseeker.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.marioioannou.showseeker.navigation.tabs.PopularTab
import com.marioioannou.showseeker.navigation.tabs.UpcomingTab
import com.marioioannou.showseeker.ui.theme.ShowSeekerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowSeekerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    BottomNavigationBar()

                }
            }
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab){

    val tabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = {
            tabNavigator.current = tab
        },
        icon = {
            tab.options.icon?.let{painter ->
                Icon(
                    painter = painter,
                    contentDescription = tab.options.title
                )
            }
        },
        label = {
            Text(text = tab.options.title)
        }
    )
}

@Composable
private fun BottomNavigationBar(){
    TabNavigator(tab = PopularTab){
        Scaffold(
            bottomBar = {
                NavigationBar {
                    TabNavigationItem(tab = PopularTab)
                    TabNavigationItem(tab = UpcomingTab)
                }
            }
        ) {
            Box(
                modifier = Modifier.padding(bottom = it.calculateTopPadding())
            )
            CurrentTab()
        }
    }
}