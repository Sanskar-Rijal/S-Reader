package com.example.books.Screens.details

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.books.R
import com.example.books.components.StylishButton
import com.example.books.data.DataorException
import com.example.books.model.Item
import com.example.books.model.Sbook
import com.example.books.navigation.ReaderScreens
import com.example.books.widgets.AppBarbysans
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay


@Composable
fun BookDetailScreen(navController: NavController=NavController(LocalContext.current),
                     bookId:String="hehehehe",
                     viewModel: DetailsViewModel= hiltViewModel()
) {
    Scaffold(
        topBar = {
            AppBarbysans(
                title = "Book Details",
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                showProfile = false,
                navController = navController
            ) {
                navController.popBackStack()
            }
        }
    ) { contentpadding ->
        Surface(
            modifier = Modifier
                .padding(contentpadding)
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier.padding( 12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
/** we can use this also if we use return repository.getbookinfo in viewmodel
                val bookInfo = produceState<DataorException<Item>>(initialValue = DataorException.Loading()){
                    value = viewModel.getbookInfo(bookId = bookId)
                }.value
               // val data= viewModel.getbookInfo(bookId = bookId)
                Log.d("samsth", "BookDetailScreen: ${bookInfo.data.toString()}")
                if(bookInfo.data !=null){
                    Text(text = "hi details are ${bookInfo.data.volumeInfo.title}")
                }
**/
                // Trigger the fetch when the screen is loaded

                LaunchedEffect(key1 = bookId) {
    viewModel.FetchBooks(bookId)
}
                val bookInfo= viewModel.bookInfo
                if(bookInfo ==null){
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    Text(text = "Loading.......")
                }else
                {
                    Log.d("samridheee", "BookDetailScreen: $bookInfo ")
                    ShowBookDetails(bookInfo,navController)
                }
            }
        }
    }
}


@Composable
fun ShowBookDetails(bookinfo:Item,navController: NavController){

    val bookData = bookinfo.volumeInfo
    val id=bookinfo.id

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(
                    if(bookinfo.volumeInfo?.imageLinks?.smallThumbnail.isNullOrEmpty()){
                        "https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg"
                    }
                            else
                    bookinfo.volumeInfo?.imageLinks?.smallThumbnail
                )
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.dummy),
            contentDescription = "image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(180.dp)
                .width(130.dp)
                .padding(10.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = bookData?.title?:"No Title",
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 15,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(5.dp)
            )

            Text(
                text = "Author: ${bookData?.authors?.joinToString()?:"No Author"}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
    Text(text ="Total number of pages: ${bookData?.pageCount.toString()?:"Unknown"}",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(5.dp))

    Text(text ="Categories: ${bookData?.categories?.joinToString()?:"Unknown"}",
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 5,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(5.dp))

    Text(text = "Published Date: ${bookData?.publishedDate?:"khaiii,Kei na kei ta ho"}",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(5.dp))

    Spacer(modifier = Modifier.height(7.dp))

    val CleanDescription = HtmlCompat.fromHtml(bookData?.description?:"Description xaina hai yo book ko,'nahi nahi haam yaa kya karne aye hae?' ma lekhdinxu description\uD83D\uDE0E,'sanskar nam vako manxe pro,gyani,reliable hunxan rey dhanaybadh',patyauna alik garo huna sakxa tara yo mathi ko book lae yei vanxa hai,kasri vanxa? book padhe paxi tha hunxa commonsense \uD83D\uDE07",
        HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    //using LazyColumn for description

    val context= LocalContext.current
    val resources=context.resources
    val displayMetrics = resources.displayMetrics
    val screenHeight=displayMetrics.heightPixels.dp.times(0.09f)

    Surface(modifier = Modifier
        .height(screenHeight)
        .padding(10.dp),
        shape = RectangleShape,
        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.tertiaryContainer)
    ) {
    LazyColumn(modifier = Modifier.padding(3.dp)) {
        item {
            //now we are removing the HTML TAGS
            Text(text = CleanDescription?:"No description",
                modifier = Modifier.padding(4.dp))
            }
        }
    }

    Row(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly) {

        StylishButton(label = "Cancel"){
            navController.popBackStack()
        }

        StylishButton(label = "Save"){
            //save book to firebase
            val book = Sbook(title = bookData?.title?:"No title",
                authors = bookData?.authors?.joinToString()?:"No authors",
                descrption = bookData?.description?:"No description",
                category = bookData?.categories?.joinToString()?:"Unknown",
                notes = "",
                photoUrl = bookData?.imageLinks?.thumbnail?:"https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg",
                publishedDate = bookData?.publishedDate?:"Unknown",
                pagecount = bookData?.pageCount?.toString()?:"Unknown",
                rating = 0.0,
                googleBookId = id,
                userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
                )
            SavetoFirebase(book,navController)
        }


    }
}


fun SavetoFirebase(book:Sbook,navController: NavController){
    val db = FirebaseFirestore.getInstance()
    val dbcollection =db.collection("books")

    if (book.toString().isNotEmpty()){
        dbcollection.add(book).addOnSuccessListener {documentRef-> //contains the id of the book

            val documentId=documentRef.id //moving the id to variable documentid

            dbcollection.document(documentId) //then again addingn it to collection
                .update(hashMapOf("id" to documentId) as Map<String, Any>)
                .addOnCompleteListener{task->
                    if(task.isSuccessful){
                        navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                    }
                }
        }.addOnFailureListener{
            Log.d("update", "SavetoFirebase: ${it.message} ")
        }

    }
}