package com.emmanuel.demo_nyt.presentation.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector


//Data class for all options in Bottom Navigation Bar
data class BottomNavigationItem(
    val label: String = "",
    val icon: ImageVector = Icons.Filled.Home,
    val route: String = ""
) {
    fun bottomNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Home",
                icon = Icons.Filled.Home,
                route = BottomNavigationRoutes.Home.route
            ),
            BottomNavigationItem(
                label = "Topics",
                icon = Icons.Filled.Star,
                route = BottomNavigationRoutes.Topics.route
            ),
        )
    }
}