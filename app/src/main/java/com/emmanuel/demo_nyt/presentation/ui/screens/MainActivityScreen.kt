package com.emmanuel.demo_nyt.presentation.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.emmanuel.demo_nyt.presentation.theme.DemoNYTTheme
import com.emmanuel.demo_nyt.presentation.ui.components.AppBar
import com.emmanuel.demo_nyt.presentation.ui.components.BottomNavigationBar
import com.emmanuel.demo_nyt.presentation.ui.navigation.NavigationRoutes

// Parent screen with the app bar, bottom navigation bar, theme and navigation
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainActivityScreen() {
    val isDarkTheme = isSystemInDarkTheme()
    val navController = rememberNavController()
    //Variables to validate the current screen and render or not the bottom navigation bar
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    //Theme applied to the app
    DemoNYTTheme(darkTheme = isDarkTheme) {
        Scaffold(
            topBar = { AppBar(navController = navController) },
            bottomBar = {
                if (currentDestination == NavigationRoutes.Home.route) {
                    BottomNavigationBar(navController = navController)
                }
            }
        ) { innerPadding ->

            /* Navigation configuration to navigate between screens the only screen with arguments
               is the details screen */
            NavHost(
                navController = navController,
                startDestination = NavigationRoutes.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(NavigationRoutes.Home.route) { HomeScreen(navController) }
                composable(
                    route = NavigationRoutes.Details.route,
                    arguments = listOf(
                        navArgument("title") { type = NavType.StringType },
                        navArgument("abstract") { type = NavType.StringType },
                        navArgument("url") { type = NavType.StringType },
                        navArgument("publishedDate") { type = NavType.StringType },
                        navArgument("mediaUrl") { type = NavType.StringType },
                    )
                ) { backStackEntry ->
                    val title = backStackEntry.arguments?.getString("title")
                    val abstract = backStackEntry.arguments?.getString("abstract")
                    val url = backStackEntry.arguments?.getString("url")
                    val publishedDate = backStackEntry.arguments?.getString("publishedDate")
                    val mediaUrl = backStackEntry.arguments?.getString("mediaUrl")
                    val desFacet = backStackEntry.arguments?.getString("desFacet")

                    DetailsScreen(
                        title = title,
                        abstract = abstract,
                        url = url,
                        publishedDate = publishedDate,
                        mediaUrl = mediaUrl,
                        desFacet = desFacet
                    )
                }
            }
        }
    }
}
