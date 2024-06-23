package com.emmanuel.demo_nyt.presentation.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.emmanuel.demo_nyt.presentation.theme.DemoNYTTheme
import com.emmanuel.demo_nyt.presentation.ui.components.AppBar
import com.emmanuel.demo_nyt.presentation.ui.components.BottomNavigationBar
import com.emmanuel.demo_nyt.presentation.ui.navigation.BottomNavigationRoutes

// Parent screen with the app bar, bottom navigation bar, theme and navigation
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainActivityScreen() {
    val isDarkTheme = isSystemInDarkTheme()

    DemoNYTTheme(darkTheme = isDarkTheme) {
        val navController = rememberNavController()

        Scaffold(
            topBar = { AppBar(navController = navController) },
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = BottomNavigationRoutes.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(BottomNavigationRoutes.Home.route) { HomeScreen(navController) }
                composable(BottomNavigationRoutes.Topics.route) { TopicsScreen(navController) }
                composable(BottomNavigationRoutes.Details.route) { DetailsScreen() }
            }
        }
    }
}