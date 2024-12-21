package com.example.books.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.books.Screens.SplashScreen
import com.example.books.Screens.home.Home
import com.example.books.Screens.login.LoginScreen
import com.example.books.Screens.login.LoginViewModel
import com.example.books.components.NotificationMessage

@Composable
fun ReaderNavigation(){
    val viewModel:LoginViewModel= hiltViewModel()
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

    }
}