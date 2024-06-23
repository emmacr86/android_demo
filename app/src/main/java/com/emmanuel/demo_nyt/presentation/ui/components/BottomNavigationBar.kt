package com.emmanuel.demo_nyt.presentation.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.emmanuel.demo_nyt.presentation.theme.PrimaryColor
import com.emmanuel.demo_nyt.presentation.ui.navigation.BottomNavigationItem

//Bottom navigation bar for the app iterates all bottom navigation items
@Composable
fun BottomNavigationBar(navController: NavHostController) {

    var navigationSelectedItem by remember { mutableIntStateOf(0) }

    NavigationBar(
        containerColor = PrimaryColor,
    ) {
        BottomNavigationItem().bottomNavigationItems()
            .forEachIndexed { index, navigationItem ->
                NavigationBarItem(
                    selected = index == navigationSelectedItem,
                    label = {
                        Text(
                            text = navigationItem.label,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            letterSpacing = 0.25.sp,
                            color = Color.White
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = navigationItem.icon,
                            contentDescription = navigationItem.label,
                        )
                    },
                    onClick = {
                        navigationSelectedItem = index
                        navController.navigate(navigationItem.route)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryColor,
                        unselectedIconColor = Color.White,
                        unselectedTextColor = Color.White,
                    ),
                )
            }
    }

}