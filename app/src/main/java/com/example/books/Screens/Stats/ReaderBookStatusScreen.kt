package com.example.books.Screens.Stats

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material.icons.sharp.Villa
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.books.Screens.home.HomeScreenViewmodel
import com.example.books.model.Sbook
import com.example.books.widgets.AppBarbysans
import com.google.firebase.auth.FirebaseAuth

@Composable
fun StatusScreen(navController: NavController= NavController(LocalContext.current),viewmodel: HomeScreenViewmodel= hiltViewModel()
){
    Scaffold (
        topBar = {
            AppBarbysans(title = "Book Stats", showProfile = false,
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                navController = navController){
                navController.popBackStack()
            }
        }){contentPadding->
        Surface(modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()){

            val books:List<Sbook>
            val currentuser = FirebaseAuth.getInstance().currentUser
            //now we will show the books that have only been read
            books = if(!viewmodel.list.isNullOrEmpty()){
                viewmodel.list.filter {sbook ->
                    sbook.userId == currentuser?.uid.toString()
                }
            }else{
                emptyList()
            }

            Column {

                Row {

                 //   Box(modifier = Modifier
                   //     .padding(10.dp)) {

//                        Icon(imageVector = Icons.Sharp.Villa,
//                            contentDescription = "Icon")

                        Text(text = "Hello ${currentuser?.email?.split("@")?.get(0)}",
                            modifier = Modifier.padding(10.dp),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            maxLines = 1,
                            overflow = TextOverflow.Clip)
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth()
                            .padding(4.dp),
                        shape= CircleShape,
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                    ) {
                         val finishedreadingBookList :List<Sbook> =
                             if(!viewmodel.list.isNullOrEmpty()){
                                 books.filter {sbook ->
                                     sbook.userId == currentuser?.uid && sbook.finishedReading !=null
                                 }
                             }else{
                                 emptyList()
                             }

                        val currentlyreadingbook=books.filter { sbook ->

                            sbook.StaredReading !=null && sbook.finishedReading == null

                        } //looking for the book that user is currerntly reading


                        Column(modifier = Modifier.padding(start = 25.dp, top = 4.dp, bottom = 4.dp),
                            horizontalAlignment = Alignment.Start) {
                            Text(text = "Your Stats", style = MaterialTheme.typography.titleLarge)
                            HorizontalDivider(modifier = Modifier.padding(top = 3.dp, bottom = 3.dp),
                                color = MaterialTheme.colorScheme.onBackground)
                            Text(text = "You're reading: ${currentlyreadingbook.size} books",
                                style = MaterialTheme.typography.labelLarge)
                            Text(text = "You've read: ${finishedreadingBookList.size} books",
                                style = MaterialTheme.typography.labelLarge)

                        }




                    }
                //outside of column




                }
            }

        }
    }
