package com.example.books.navigation

import android.net.http.UrlRequest.Status
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.books.Screens.Search.SearchScreen
import com.example.books.Screens.SplashScreen
import com.example.books.Screens.Stats.StatusScreen
import com.example.books.Screens.home.Home
import com.example.books.Screens.login.LoginScreen
import com.example.books.Screens.login.LoginViewModel
import com.example.books.components.NotificationMessage

@Composable
fun ReaderNavigation(){
    val viewModel:LoginViewModel= viewModel()
    NotificationMessage(viewModel)

    val navController= rememberNavController()

    NavHost(navController = navController,
        startDestination = ReaderScreens.SplashScreen.name) {

        composable(ReaderScreens.SplashScreen.name){
            SplashScreen(navController)
        }

        composable(ReaderScreens.LoginScreen.name){
            LoginScreen(navController)
        }

        composable(ReaderScreens.ReaderHomeScreen.name){
            Home(navController)
        }
        composable(ReaderScreens.StatsScreen.name){
            StatusScreen(navController)
        }

        composable(ReaderScreens.SearchScreen.name){
            SearchScreen(navController)
        }

    }
}