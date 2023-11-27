package com.example.docln

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.docln.plugins.Graph
import com.example.docln.screens.ChangePasswordScreen
import com.example.docln.screens.NovelDetailScreen
import com.example.docln.screens.ChapterScreen
import com.example.docln.screens.FollowScreen
import com.example.docln.screens.HomeScreen
import com.example.docln.screens.LoginScreen
import com.example.docln.screens.RankingScreen
import com.example.docln.screens.RegisterScreen
import com.example.docln.screens.SearchScreen
import com.example.docln.screens.sensorScreen
import com.example.docln.ui.theme.DocLNTheme


class MainActivity : ComponentActivity() {
    private val Context.dataStore by preferencesDataStore(
        name = "readerSettings"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Graph.provide(this)
        setContent {
            DocLNTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun Navigation() {
//    val chapterScreenViewModel = viewModel<ChapterViewModel>()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Home.route ) {
        composable(route = Routes.Home.route) {
            HomeScreen(navController)
        }
        composable(
            route = Routes.NovelDetail.route + "/{novelID}",
            arguments = listOf(
                navArgument("novelID") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = false
                }
            )
        ) {
            entry -> NovelDetailScreen(navController, novelID = entry.arguments?.getString("novelID"))
        }

        composable(
            route = Routes.Chapter.route + "/{novelID}/{chapID}",
            arguments = listOf(
                navArgument("novelID") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = false
                },
                navArgument("chapID") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = false
                }
            )
        ) {
            entry -> ChapterScreen(navController, novelID = entry.arguments?.getString("novelID") , chapterID = entry.arguments?.getString("chapID"))
        }
//        qa sửa note
        composable(
            route = Routes.Login.route,
        ) {
                entry -> LoginScreen(navController, account_id = entry.arguments?.getString("account_id"))
        }
        composable(
            route = Routes.Register.route,
        ) {
                entry -> RegisterScreen(navController)
        }
        composable(
            route = Routes.Search.route,
        ) {
                entry -> SearchScreen(navController)
        }
        composable(
            route = Routes.ChangePassword.route,
        ) {
                entry->
            ChangePasswordScreen(navController)
        }
        composable(
            route = Routes.sensor.route,
        ) {
                entry->
            sensorScreen(navController)
        }
        composable(
            route = Routes.Follow.route,
        ) {
                entry -> FollowScreen(navController)
        }
//        thêm bảng xếp hạng mới
        composable(
            route = Routes.Ranking.route,
        ) {

                entry ->
            val rankingItems = null;
            rankingItems?.let { RankingScreen(navController, it) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DocLNTheme {
        Navigation()
    }
}