package com.emmanuel.demo_nyt.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Light color scheme
private val LightColors = lightColorScheme(
    primary = PrimaryColor,
    primaryContainer = LightPrimaryVariantColor,
    secondary = LightSecondaryColor,
    secondaryContainer = LightBackgroundColor,
    background = LightBackgroundColor,
    surface = LightSurfaceColor,
    onPrimary = LightOnPrimaryColor,
    onSecondary = LightOnSecondaryColor,
    onBackground = LightOnBackgroundColor,
    onSurface = LightOnSurfaceColor,
)

// Dark color scheme
private val DarkColors = darkColorScheme(
    primary = PrimaryColor,
    primaryContainer = DarkPrimaryVariantColor,
    secondary = DarkSecondaryColor,
    secondaryContainer = DarkBackgroundColor,
    background = DarkBackgroundColor,
    surface = DarkSurfaceColor,
    onPrimary = DarkOnPrimaryColor,
    onSecondary = DarkOnSecondaryColor,
    onBackground = DarkOnBackgroundColor,
    onSurface = DarkOnSurfaceColor,
)

@Composable
fun DemoNYTTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}