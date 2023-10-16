package com.example.docln

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.docln.screens.NovelDetailScreen
import com.example.docln.screens.ChapterScreen
import com.example.docln.screens.HomeScreen
import com.example.docln.screens.LoginScreen
import com.example.docln.screens.RegisterScreen
import com.example.docln.screens.ModalNavigationDrawerSample
import com.example.docln.ui.theme.DocLNTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Home.route ) {
        composable(route = Routes.Home.route) {
            ModalNavigationDrawerSample(navController)
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
            route = Routes.Chapter.route + "/{chapID}",
            arguments = listOf(
                navArgument("chapID") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = false
                }
            )
        ) {
            entry -> ChapterScreen(chapterID = entry.arguments?.getString("chapID"))
        }
//        qa sửa note
        composable(
            route = Routes.Login.route,
        ) {
                entry -> LoginScreen( navController,  account_id = entry.arguments?.getString("account_id"))
        }
        composable(
            route = Routes.Register.route,
        ) {
                entry -> RegisterScreen( )
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