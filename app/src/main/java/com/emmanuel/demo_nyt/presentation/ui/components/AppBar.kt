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
import com.emmanuel.demo_nyt.presentation.ui.navigation.BottomNavigationRoutes

//App bar for the application
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    val currentLabel = currentDestination?.substringAfterLast('/')

    // Check if the current destination is the details screen for hide/show button
    val canPopBackStack =
        navBackStackEntry != null && navBackStackEntry?.destination?.route == BottomNavigationRoutes.Details.route

    TopAppBar(
        title = { Text(text = currentLabel.toString(), color = Color.White) },
        navigationIcon = {
            if (canPopBackStack) {
                IconButton(
                    onClick = {
                        navController.previousBackStackEntry
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