package com.letusneil.hackernews.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.letusneil.hackernews.ui.components.GlassCard
import com.letusneil.hackernews.ui.components.LiquidGlassBackground
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
    val storyItem = item.storyItem

    GlassCard(
        modifier = modifier.fillMaxWidth(),
        onClick = { onClick(item) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = storyItem.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))

            storyItem.url?.let { url ->
                Text(
                    text = extractDomain(url),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF64B5F6),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(6.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${storyItem.points} points",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFFFFB74D)
                )
                Text(
                    text = "${storyItem.commentCount} comments",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = storyItem.timeAgo,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.5f)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "by ${storyItem.author}",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF81D4FA)
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
        LiquidGlassBackground {
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
                onClick = {},
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StoryItemNoUrlPreview() {
    HackernewsTheme {
        LiquidGlassBackground {
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
                onClick = {},
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
