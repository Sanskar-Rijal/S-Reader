package com.example.books.Screens.Stats

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material.icons.sharp.Villa
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.books.R
import com.example.books.Screens.home.HomeScreenViewmodel
import com.example.books.model.Sbook
import com.example.books.navigation.ReaderScreens
import com.example.books.utils.formatDate
import com.example.books.widgets.AppBarbysans
import com.example.books.widgets.SearchListCard
import com.google.firebase.auth.FirebaseAuth

@Composable
fun StatusScreen(navController: NavController= NavController(LocalContext.current),viewmodel: HomeScreenViewmodel= hiltViewModel()
){
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

            Column(modifier = Modifier.padding(10.dp)) {

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
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        shape= CircleShape,
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                    ) {


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
                Spacer(modifier = Modifier.height(10.dp))
                //outside of column making scrollable column to see read boooks
                if(viewmodel.isloading){
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    Text(text = "Loading.......")
                }else{

                    HorizontalDivider(color = MaterialTheme.colorScheme.onBackground)

                    LazyColumn(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                        contentPadding = PaddingValues(16.dp)){

                        items(finishedreadingBookList){ book->
                            BookStats(book,navController){
                                navController.navigate(ReaderScreens.UpdateScreen.name+"/$it")
                                Log.d("smriti", "StatusScreen: $it")
                            }
                        }
                        }
                    }
                }

                }
            }

        }

@Composable
fun BookStats(book: Sbook,navController: NavController,
              onPressDetails: (String) -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                onPressDetails(book.googleBookId!!)
            },
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(3.dp),
            verticalAlignment = Alignment.Top
        ) {
           // Log.d("mentalism", "SearchListCard: the book image is ${book.volumeInfo?.imageLinks?.smallThumbnail ?: "no image"} ")

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        if (book.photoUrl.isNullOrEmpty())
                            "https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg"
                        else
                            book.photoUrl                    )
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.dummy),
                contentDescription = "hehehe",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(120.dp)
                    .width(90.dp)
                    .padding(4.dp)
            )
            Spacer(Modifier.width(20.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
//                Row(modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween){
                    Text(
                        text = book.title ?: "No Title",
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Clip,
                        color = MaterialTheme.colorScheme.onBackground
                    )

//                    if(book.rating!! >=4){
//                        Icon(imageVector = Icons.Default.ThumbUp, contentDescription = "icon")
//                    }else{
//                        Box{}
//                    }
//
//                }

                Text(
                    text = "Author: ${book.authors?: "Unknown"}",
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "Started at: ${formatDate(book.StaredReading!!)?: "Unknown Date"}",
                    modifier = Modifier.padding(2.dp),
                    softWrap = true,
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "Finished at ${formatDate(book.finishedReading!!)?: "Unknown Date"}",
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

