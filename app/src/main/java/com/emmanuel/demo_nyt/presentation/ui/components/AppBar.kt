package com.emmanuel.demo_nyt.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.emmanuel.demo_nyt.presentation.theme.PrimaryColor
import com.emmanuel.demo_nyt.presentation.ui.navigation.NavigationRoutes

/**
 * App Bar.
 *
 * @param navController receive the navigation controller to navigate between screens.
 * @return a composable that displays the app bar and set the title depending on the current screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val routeTitleMap = mapOf(
        NavigationRoutes.Home.route to "Home",
        "Details" to "Details"
    )

    // Set the title depending on the current screen
    val currentLabel = when {
        currentDestination != null && routeTitleMap.containsKey(currentDestination) -> {
            routeTitleMap[currentDestination]?:"App Demo"
        }
        currentDestination?.startsWith("Details") == true -> {
            "Details"
        }
        else -> {
            "App Demo"
        }
    }

    val canPopBackStack = navController.previousBackStackEntry != null
    val isDetailsScreen = currentDestination?.startsWith("Details") == true

    TopAppBar(
        title = { Text(text = currentLabel, color = Color.White) },
        navigationIcon = {
            if (isDetailsScreen && canPopBackStack) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Navigation Icon",
                        tint = Color.White,
                    )
                }
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = PrimaryColor
        ),
    )
}