package com.example.books.Screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.books.navigation.ReaderScreens
import com.example.books.widgets.AppBarbysans

@Preview
@Composable
fun BookDetailScreen(navController: NavController=NavController(LocalContext.current), bookId:String="hehehehe"){
    Scaffold(
        topBar = {
            AppBarbysans(title = "Book Details",
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                showProfile = false,
                navController = navController){
                navController.navigate(ReaderScreens.SearchScreen.name)
            }
        }
    ){contentpadding->
        Surface(modifier = Modifier
            .padding(contentpadding)
            .fillMaxSize()) {

            Column(modifier = Modifier.padding(top=12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {

                Text(text ="Book id is $bookId",
                    color = MaterialTheme.colorScheme.onBackground)
            }

        }
    }
}