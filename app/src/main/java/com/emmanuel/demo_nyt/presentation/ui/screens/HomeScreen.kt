package com.emmanuel.demo_nyt.presentation.ui.screens

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.emmanuel.demo_nyt.presentation.ui.components.EmptyBackground
import com.emmanuel.demo_nyt.presentation.ui.components.ErrorDialog
import com.emmanuel.demo_nyt.presentation.ui.components.HomeCardView
import com.emmanuel.demo_nyt.presentation.ui.navigation.NavigationRoutes
import com.emmanuel.demo_nyt.presentation.viewmodel.HomeViewModel
import com.emmanuel.demo_nyt.presentation.viewmodel.HomeViewModelFactory
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Home Screen to display the articles.
 *
 * @param navController is the navigation controller to navigate between screens.
 * @param homeViewModel is the view model to display the articles and is not required to passed as parameter is just for the view model factory.
 * @return a complete screen with the articles and all components on it.
 */
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(
            application = LocalContext.current.applicationContext as Application
        )
    )
) {
    val articles by homeViewModel.articles.observeAsState(listOf())
    val onRequestResult by homeViewModel.onRequestResult.observeAsState()
    val listState = rememberLazyListState()

    // Display the alert dialog if the request result is an error
    onRequestResult?.let { result ->
        when (result) {
            is HomeViewModel.OnRequestResult.Error -> {
                ErrorDialog(
                    onDialogDismiss = { homeViewModel.resetResponse() },
                    message = result.message
                )
            }

            is HomeViewModel.OnRequestResult.Success -> {
                Log.d("HOME_SCREEN", "REQUEST SUCCESS")
            }
        }
    }

    articles?.let { list ->
        // Display the articles if the list is not empty
        if (list.isNotEmpty()) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(list) { article ->
                    HomeCardView(
                        article = article,
                        onClick = {
                            navController.navigate(
                                NavigationRoutes.Details.createRoute(
                                    title = article?.title ?: "",
                                    abstract = article?.abstract ?: "",
                                    url = encodeUrl(article?.url),
                                    publishedDate = article?.published_date ?: "",
                                    mediaUrl = encodeUrl(article?.multimedia?.get(0)?.url),
                                    desFacet = (article?.des_facet ?: "").toString()
                                )
                            )
                        },
                    )
                }
            }
        } else {
            // Display the empty background with and icon if the list is empty
            EmptyBackground(
                icon = Icons.Default.Home
            )
        }
    }

}

/* Navigation in compose require to encodes the url to avoid special characters is possible to add a
   different url if the url from the request fail. In this case I returned in the elvis operator the
   empty string for show a blue background */
fun encodeUrl(url: String?): String {
    return try {
        URLEncoder.encode(url ?: "https://www.google.com/", StandardCharsets.UTF_8.toString())
    } catch (e: Exception) {
        "https://www.google.com/"
    }
}