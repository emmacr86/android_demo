package com.emmanuel.demo_nyt.presentation.ui.navigation

//Class with all navigation routes of the app
sealed class BottomNavigationRoutes(val route: String) {
    data object Home : BottomNavigationRoutes("Home")
    data object Topics : BottomNavigationRoutes("Topics")
    data object Details : BottomNavigationRoutes("Details")
}