package com.letusneil.hackernews.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.letusneil.hackernews.ui.theme.HackernewsTheme
import com.letusneil.shared.domain.model.StoryCategory
import com.letusneil.shared.domain.model.StoriesFeedItem
import com.letusneil.shared.domain.model.StoryItem

@Composable
fun StoryItem(
    item: StoriesFeedItem,
    onClick: (StoriesFeedItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val newsItem = item.storyItem

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(item) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = newsItem.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            newsItem.url?.let { url ->
                Text(
                    text = extractDomain(url),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${newsItem.points} points",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${newsItem.commentCount} comments",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = newsItem.timeAgo,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "by ${newsItem.author}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun extractDomain(url: String): String {
    return try {
        val withoutProtocol = url.substringAfter("://")
        withoutProtocol.substringBefore("/").removePrefix("www.")
    } catch (e: Exception) {
        url
    }
}

@Preview(showBackground = true)
@Composable
private fun StoryItemPreview() {
    HackernewsTheme {
        StoryItem(
            item = StoriesFeedItem(
                storyItem = StoryItem(
                    id = "1",
                    title = "Show HN: I built a tool to automatically optimize React performance",
                    url = "https://github.com/example/react-optimizer",
                    author = "johndoe",
                    points = 256,
                    commentCount = 89,
                    timeAgo = "3 hours ago"
                ),
                category = StoryCategory.TOP
            ),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StoryItemNoUrlPreview() {
    HackernewsTheme {
        StoryItem(
            item = StoriesFeedItem(
                storyItem = StoryItem(
                    id = "2",
                    title = "Ask HN: What are the best resources for learning system design?",
                    url = null,
                    author = "curious_dev",
                    points = 142,
                    commentCount = 67,
                    timeAgo = "5 hours ago"
                ),
                category = StoryCategory.ASK
            ),
            onClick = {}
        )
    }
}
