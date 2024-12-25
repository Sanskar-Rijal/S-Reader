package com.example.books.Screens.Search

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.books.navigation.ReaderScreens
import com.example.books.widgets.AppBarbysans

@Preview
@Composable
fun SearchScreen(navController: NavController=NavController(LocalContext.current)){
    Scaffold(topBar = {
        AppBarbysans(title = "Search Books", showProfile = false,
            navController = NavController(LocalContext.current),
            icon = Icons.AutoMirrored.Filled.ArrowBack){
            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
        }
    }){
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {

        }
    }
}