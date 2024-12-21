package com.example.books.navigation


//www.google.com/sign-in
//route==signin

enum class ReaderScreens {
    SplashScreen,
    LoginScreen,
    CreateAccountScreen,
    ReaderHomeScreen,
    SearchScreen,
    DetailScreen,
    UpdateScreen,
    StatsScreen;
    companion object{
        fun fromRoute(route:String):ReaderScreens =when(route?.substringBefore( "/")){
            SplashScreen.name->SplashScreen
            LoginScreen.name->LoginScreen
            CreateAccountScreen.name->CreateAccountScreen
            ReaderHomeScreen.name->ReaderHomeScreen
            SearchScreen.name->SearchScreen
            DetailScreen.name->DetailScreen
            UpdateScreen.name->UpdateScreen
            StatsScreen.name->StatsScreen
            null->ReaderHomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognised")
        }
    }
}