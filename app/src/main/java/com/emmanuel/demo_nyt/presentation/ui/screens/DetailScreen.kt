package com.emmanuel.demo_nyt.presentation.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.emmanuel.demo_nyt.R
import com.emmanuel.demo_nyt.presentation.theme.ImageBackgroundColor
import com.emmanuel.demo_nyt.presentation.theme.PrimaryColor

/**
 * Details Screen to display the article details.
 *
 * @param title is the string to display the title of the article.
 * @param abstract is the string to display the abstract of the article.
 * @param url is used for redirect to the article in new yor times by the visit place button.
 * @param publishedDate is the string to display the published date of the article.
 * @param mediaUrl is the string to display the image of the article using coil library (The endpoint comes with 3 different sizes so I used the first one with better quality).
 * @param desFacet is a list of the topics of the article converted to string and used for display the topics in the chip.
 * @return a composable that displays the details of the article.
 */
@Composable
fun DetailsScreen(
    title: String?,
    abstract: String?,
    url: String?,
    publishedDate: String?,
    mediaUrl: String?,
    desFacet: String?,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Image loaded with coil library if the image has issues loading a blue background is shown
        if (mediaUrl?.isNotEmpty() == true) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(mediaUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                Modifier
                    .background(color = ImageBackgroundColor)
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.FillBounds,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Card(
                shape = MaterialTheme.shapes.medium,
                border = null,
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp,
                ),
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Title of the article
                    Text(
                        text = title ?: "Article title",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.25.sp
                    )

                    // Share button it takes the title and the abstract and the url of the article
                    TextButton(onClick = {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(
                                Intent.EXTRA_SUBJECT,
                                context.getString(R.string.share_extra_subject)
                            )
                            putExtra(Intent.EXTRA_TEXT, "$title: $abstract $url")
                        }
                        val shareIntent = Intent.createChooser(intent, "Share using")
                        context.startActivity(shareIntent)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = null,
                            tint = PrimaryColor,
                        )
                    }

                }

                // Abstract of the article
                Text(
                    text = abstract
                        ?: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed aliquam nisi et diam vehicula mollis.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                )

                // Topics of the article
                val chipItems =
                    desFacet?.replace("[", "")?.replace("]", "")?.split(',')?.map { it.trim() }

                Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)) {
                    chipItems?.forEach { item ->
                        AssistChip(
                            onClick = {
                                // Is possible to add events on each article topic
                            },
                            label = {
                                Text(
                                    text = item,
                                    color = PrimaryColor,
                                    letterSpacing = 0.25.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            },
                        )
                    }
                }

                // Published date of the article
                Text(
                    text = ("Published on: " + publishedDate?.substring(0, 10)),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                )

                // Visit place button redirects to a browser with the url of the article
                Button(
                    onClick = {
                        val uri = Uri.parse(url)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        if (intent.resolveActivity(context.packageManager) != null) {
                            context.startActivity(intent)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    colors = ButtonDefaults.buttonColors(PrimaryColor)
                ) {
                    Text(text = "Visit place")
                }

            }

        }

    }

}