package com.letusneil.hackernews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.letusneil.hackernews.navigation.HomeRoute
import com.letusneil.hackernews.navigation.StoryDetailRoute
import com.letusneil.hackernews.ui.detail.StoryDetailScreen
import com.letusneil.hackernews.ui.home.HomeScreen
import com.letusneil.hackernews.ui.theme.HackernewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HackernewsTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = HomeRoute
                ) {
                    composable<HomeRoute> {
                        HomeScreen(
                            onStoryClick = { story ->
                                navController.navigate(
                                    StoryDetailRoute(
                                        storyId = story.storyItem.id,
                                        title = story.storyItem.title,
                                        url = story.storyItem.url,
                                        author = story.storyItem.author,
                                        points = story.storyItem.points,
                                        commentCount = story.storyItem.commentCount,
                                        timeAgo = story.storyItem.timeAgo
                                    )
                                )
                            }
                        )
                    }

                    composable<StoryDetailRoute> { backStackEntry ->
                        val route = backStackEntry.toRoute<StoryDetailRoute>()
                        StoryDetailScreen(
                            route = route,
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}