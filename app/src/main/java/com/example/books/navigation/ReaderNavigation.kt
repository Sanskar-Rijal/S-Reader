package com.example.books.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.books.Screens.SplashScreen
import com.example.books.Screens.home.Home

@Composable
fun ReaderNavigation(){
    val navController= rememberNavController()
    NavHost(navController = navController,
        startDestination = ReaderScreens.SplashScreen.name) {

        composable(ReaderScreens.SplashScreen.name){
            SplashScreen(navController)
        }

        composable(ReaderScreens.ReaderHomeScreen.name){
            Home()
        }

    }
}