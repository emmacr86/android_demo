package com.emmanuel.demo_nyt.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.emmanuel.demo_nyt.domain.model.Article
import com.emmanuel.demo_nyt.domain.model.Multimedia
import com.emmanuel.demo_nyt.presentation.theme.ImageBackgroundColor
import com.emmanuel.demo_nyt.presentation.theme.PrimaryColor

/**
 * Card view for display articles.
 *
 * @param article is the article to display, commonly is used for display the title and the abstract of the article. (Is optional add more data)
 * @param modifier is optional in order to set different attributes to the component.
 * @param onClick is the event after the user click on the "read more..." option.
 * @return a composable that displays the card view in home screen.
 */
@Composable
fun HomeCardView(
    article: Article?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        border = null,
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        ),
    ) {
        Column {

            //Title of the article
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.fillMaxWidth()) {
                    Text(
                        text = article?.title ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        overflow = TextOverflow.Ellipsis,
                        letterSpacing = 0.25.sp,
                        maxLines = 2,
                    )
                }
            }

            //Image loaded with coil library if the image has issues loading a blue background is shown
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(article?.multimedia?.get(0)?.url.toString())
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                Modifier
                    .background(color = ImageBackgroundColor)
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop,
            )

            //Abstract of the article reduced a 2 lines
            Row(Modifier.padding(start = 16.dp, end = 24.dp, top = 16.dp)) {
                Text(
                    text = article?.abstract ?: "",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    letterSpacing = 0.25.sp,
                )
            }

            //Box to show the read more button aligned to the end of the card
            Box(
                Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
            ) {
                Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                    TextButton(onClick = {
                        onClick()
                    }) {
                        Text(
                            text = "Read more...",
                            color = PrimaryColor,
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null,
                            tint = PrimaryColor,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeCardViewPreview() {
    val multimedia2 = Multimedia(
        url = "",
        format = "Large Thumbnail",
        height = 150,
        width = 150,
        type = "image",
        subtype = "photo",
        caption = "",
        copyright = ""
    )
    val article = Article(
        section = "World",
        subsection = "Europe",
        title = "Article Title",
        abstract = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
        url = "",
        uri = "",
        byline = "",
        item_type = "",
        updated_date = "",
        created_date = "",
        published_date = "",
        material_type_facet = "",
        kicker = "",
        des_facet = listOf("Politics", "Europe", "Economy"),
        org_facet = listOf("European Union", "NATO"),
        per_facet = listOf("John", "Jane Smith"),
        geo_facet = listOf("Germany", "France"),
        multimedia = listOf(multimedia2),
        short_url = ""
    )
    MaterialTheme {
        HomeCardView(
            article = article,
            onClick = { },
            modifier = Modifier.padding(16.dp)
        )
    }
}