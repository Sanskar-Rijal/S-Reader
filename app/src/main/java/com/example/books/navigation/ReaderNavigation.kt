package com.example.books.navigation

import android.net.http.UrlRequest.Status
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentManager.BackStackEntry
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.books.Screens.Search.SearchScreen
import com.example.books.Screens.Search.SearchViewModel
import com.example.books.Screens.SplashScreen
import com.example.books.Screens.Stats.StatusScreen
import com.example.books.Screens.details.BookDetailScreen
import com.example.books.Screens.home.Home
import com.example.books.Screens.home.HomeScreenViewmodel
import com.example.books.Screens.login.LoginScreen
import com.example.books.Screens.login.LoginViewModel
import com.example.books.Screens.update.BookUpdateScreen
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
            var homeViewmodel = hiltViewModel<HomeScreenViewmodel>()
            Home(navController,homeViewmodel)
        }
        composable(ReaderScreens.StatsScreen.name){
            StatusScreen(navController)
        }

        composable(ReaderScreens.SearchScreen.name){
            val searchHiltViewmodel= hiltViewModel<SearchViewModel>()
            SearchScreen(navController,searchHiltViewmodel)
        }

        //we want bookId from SearchScreen
        //www.google.com/bookid="SpiderMan" we are passing the city variable like the url in web
        val route1=ReaderScreens.DetailScreen.name
        composable("$route1/{bookid}",
            arguments = listOf(navArgument(name="bookid"){
                type= NavType.StringType
            })
        ){ BackStackEntry->
            BackStackEntry.arguments?.getString("bookid").let{bookid->
                if (bookid != null) {
                    BookDetailScreen(navController=navController, bookId =bookid.toString())
                }
            }
        }


        //we are passing book id from homescreen to detail screen
        val route2=ReaderScreens.UpdateScreen.name
        composable("$route2/{bookid}",
            arguments = listOf(navArgument(name = "bookid"){
                type=NavType.StringType
            })){backStackEntry->
            backStackEntry.arguments?.getString("bookid").let { bookid->
                if(bookid!=null){
                    BookUpdateScreen(navController = navController,id=bookid)
                }
            }
        }
    }
}