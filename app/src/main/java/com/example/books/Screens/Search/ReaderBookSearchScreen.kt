package com.example.books.Screens.Search

import android.graphics.drawable.Icon
import android.provider.ContactsContract.Data
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.books.components.BookNotFound
import com.example.books.data.DataorException
import com.example.books.data.EVENT
import com.example.books.model.Sbook
import com.example.books.navigation.ReaderScreens
import com.example.books.widgets.AppBarbysans
import com.example.books.widgets.SearchBar
import com.example.books.widgets.SearchListCard


@Composable
fun SearchScreen(navController: NavController,
                 searchViewModel: SearchViewModel= hiltViewModel()
                 ){

    Scaffold(topBar = {
        AppBarbysans(title = "Search Books", showProfile = false,
            navController = NavController(LocalContext.current),
            icon = Icons.AutoMirrored.Filled.ArrowBack){
           navController.popBackStack() //this would have also worked
            //navController.navigate(ReaderScreens.ReaderHomeScreen.name)
        }
    }){
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            Column(modifier = Modifier.padding(10.dp)) {

                SearchBar(){bookName-> //this lamda i.e bookName gives the Name of the Book
                    searchViewModel.SearchBooks(bookName)
                }
                ShowBooks(navController=navController)
                BookNotFound(searchViewModel)

            }
        }
    }
}

@Composable
fun ShowBooks(navController: NavController,searchViewMode: SearchViewModel= hiltViewModel()){

    val listofbooks= searchViewMode.list //we should have list of books
    if(searchViewMode.isLoading) {
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            Text(text = "Loading.......")
        }
    }
    else{
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(5.dp)
        ) {
            items(listofbooks) {
                SearchListCard(book = it, navController = navController) {bookid->
                    //onclick
                    navController.navigate(ReaderScreens.DetailScreen.name+"/$bookid")
                }
            }

        }
    }
}


