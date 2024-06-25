package com.emmanuel.demo_nyt.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

/**
 * Empty background meanwhile the data is loading from the API.
 *
 * @param icon is the icon to be displayed.
 * @param modifier is optional in order to set different attributes to the component.
 * @return a composable that displays an empty background with icon while the data is loading.
 */
@Composable
fun EmptyBackground(
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (image) = createRefs()

        Image(
            imageVector = icon,
            contentDescription = "",
            colorFilter = ColorFilter.tint(color = Color.LightGray.copy(alpha = 0.65f)),
            modifier = Modifier
                .width(250.dp)
                .heightIn(250.dp)
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

    }
}

@Preview(showBackground = true)
@Composable
fun EmptyBackgroundPreview() {
    MaterialTheme {
        EmptyBackground(
            icon = Icons.Default.Home,
            modifier = Modifier.fillMaxSize()
        )
    }
}