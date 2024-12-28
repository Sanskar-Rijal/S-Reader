package com.example.books.Screens.update

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.books.Screens.home.HomeScreenViewmodel
import com.example.books.widgets.AppBarbysans

@Preview
@Composable
fun BookUpdateScreen(navController: NavController= NavController(LocalContext.current), id:String="hehehe"){

    Scaffold(
        topBar = {
            AppBarbysans(title="Update Screen",
                showProfile = false,
                navController=navController,
                icon = Icons.AutoMirrored.Filled.ArrowBack){
                //on clicked we wish to go back
                navController.popBackStack()
            }
        }){contentpadding->
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(contentpadding)) {

            Column(modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Surface(modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                    shape = CircleShape,
                    shadowElevation = 4.dp
                ) {



                }
            }

        }

    }

}