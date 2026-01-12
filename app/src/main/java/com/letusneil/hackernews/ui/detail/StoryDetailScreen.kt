package com.letusneil.hackernews.ui.detail

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.letusneil.hackernews.navigation.StoryDetailRoute
import com.letusneil.hackernews.ui.components.GlassCard
import com.letusneil.hackernews.ui.components.LiquidGlassBackground
import com.letusneil.hackernews.ui.theme.HackernewsTheme
import com.letusneil.hackernews.ui.theme.LiquidGlassColors

@Composable
fun StoryDetailScreen(
    route: StoryDetailRoute,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LiquidGlassBackground(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Glass App Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LiquidGlassColors.appBarGradient)
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(LiquidGlassColors.glassWhiteLow)
                            .clickable(onClick = onBackClick),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = LiquidGlassColors.textPrimary
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Story",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = LiquidGlassColors.textPrimary
                    )
                }
            }

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Title Card
                GlassCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = route.title,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = LiquidGlassColors.textPrimary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Metadata Row 1
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            MetadataItem(
                                icon = {
                                    Icon(
                                        Icons.Default.Person,
                                        contentDescription = null,
                                        tint = LiquidGlassColors.accentCyan
                                    )
                                },
                                text = route.author,
                                textColor = LiquidGlassColors.accentCyan
                            )
                            MetadataItem(
                                icon = {
                                    Icon(
                                        Icons.Default.Schedule,
                                        contentDescription = null,
                                        tint = LiquidGlassColors.textTertiary
                                    )
                                },
                                text = route.timeAgo,
                                textColor = LiquidGlassColors.textTertiary
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Metadata Row 2
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            MetadataItem(
                                icon = {
                                    Icon(
                                        Icons.Default.ThumbUp,
                                        contentDescription = null,
                                        tint = LiquidGlassColors.accentOrange
                                    )
                                },
                                text = "${route.points} points",
                                textColor = LiquidGlassColors.accentOrange
                            )
                            MetadataItem(
                                icon = {
                                    Icon(
                                        Icons.Outlined.ChatBubbleOutline,
                                        contentDescription = null,
                                        tint = LiquidGlassColors.textSecondary
                                    )
                                },
                                text = "${route.commentCount} comments",
                                textColor = LiquidGlassColors.textSecondary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // URL Card or No URL Card
                route.url?.let { url ->
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
                        }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(LiquidGlassColors.accentBlue.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.OpenInBrowser,
                                    contentDescription = null,
                                    tint = LiquidGlassColors.accentBlue,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Open in Browser",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = LiquidGlassColors.textPrimary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = extractDomain(url),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = LiquidGlassColors.accentBlue,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                } ?: run {
                    GlassCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(LiquidGlassColors.textTertiary.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.ChatBubbleOutline,
                                    contentDescription = null,
                                    tint = LiquidGlassColors.textTertiary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "This is a text-only post without an external link.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = LiquidGlassColors.textSecondary
                            )
                        }
                    }
                }
            }
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

@Composable
private fun MetadataItem(
    icon: @Composable () -> Unit,
    text: String,
    textColor: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        icon()
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StoryDetailScreenPreview() {
    HackernewsTheme {
        StoryDetailScreen(
            route = StoryDetailRoute(
                storyId = "1",
                title = "Show HN: I built a tool to automatically optimize React performance",
                url = "https://github.com/example/react-optimizer",
                author = "johndoe",
                points = 256,
                commentCount = 89,
                timeAgo = "3 hours ago"
            ),
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StoryDetailScreenNoUrlPreview() {
    HackernewsTheme {
        StoryDetailScreen(
            route = StoryDetailRoute(
                storyId = "2",
                title = "Ask HN: What are the best resources for learning system design?",
                url = null,
                author = "curious_dev",
                points = 142,
                commentCount = 67,
                timeAgo = "7 hours ago"
            ),
            onBackClick = {}
        )
    }
}
