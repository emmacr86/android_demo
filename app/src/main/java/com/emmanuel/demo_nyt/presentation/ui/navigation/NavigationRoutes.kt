package com.emmanuel.demo_nyt.presentation.ui.navigation

//Class with all navigation routes of the app
sealed class NavigationRoutes(val route: String) {
    data object Home : NavigationRoutes("Home")
    data object Details : NavigationRoutes("Details/{title}/{abstract}/{url}/{publishedDate}/{mediaUrl}/{desFacet}") {
        fun createRoute(
            title: String,
            abstract: String,
            url: String,
            publishedDate: String,
            mediaUrl: String,
            desFacet: String
        ) = "Details/$title/$abstract/$url/$publishedDate/$mediaUrl/$desFacet"
    }
}