package com.example.books.Screens.update

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.books.R
import com.example.books.Screens.home.HomeScreenViewmodel
import com.example.books.components.Input
import com.example.books.model.Book
import com.example.books.model.Sbook
import com.example.books.widgets.AppBarbysans
import kotlin.math.log

@Composable
fun BookUpdateScreen(navController: NavController, id:String,viewmodel: HomeScreenViewmodel= hiltViewModel()) {

    var bookinfo = emptyList<Sbook>()

    if (!viewmodel.list.isNullOrEmpty()) {
        bookinfo=viewmodel.list
        Log.d("april", "BookUpdateScreen: $bookinfo ")
    }
    Scaffold(
        topBar = {
            AppBarbysans(
                title = "Update Screen",
                showProfile = false,
                navController = navController,
                icon = Icons.AutoMirrored.Filled.ArrowBack
            ) {
                //on clicked we wish to go back
                navController.popBackStack()
            }
        }) { contentpadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentpadding)
        ) {

            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                    if(bookinfo.isNullOrEmpty()){
                        LinearProgressIndicator()
                        Text(text = "Loading.......")
                    }else{
                    Surface(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        shape = CircleShape,
                        shadowElevation = 4.dp,
                        tonalElevation = 4.dp
                    ) {
                        ShowBookUpdate(Bookinfo=bookinfo,Bookid=id)
                    }

                    //making a text view to enter thoughts
                    ShowSimpleForm(bookinfo=bookinfo.first(){sbook ->
                        sbook.googleBookId == id
                    },navController=navController)
                }

            }

        }
    }
}

@Composable
fun ShowBookUpdate(Bookinfo:List<Sbook>,Bookid:String){
    Row(modifier = Modifier.padding(10.dp)) {
        //check whether data loaded or not using if statememnt

        Column(modifier = Modifier.padding(5.dp),
            verticalArrangement = Arrangement.Center) {
            CardListBook(book=Bookinfo.first(){sbook ->
                Log.d("april", "ShowBookUpdate: ${sbook.googleBookId}")
                sbook?.googleBookId == Bookid
            })
            /**
             * The code filters a list of Sbook objects (Bookinfo)
             * to find the first book whose googleBookId matches the
             * provided Bookid, and then passes that book to the
             * CardListBook composable.
             *
             * The first() function in Kotlin returns the first
             * element in the list that matches the condition provided
             * in the lambda.
             */
        }
    }
}


//making top design for books,top design
@Composable
fun CardListBook(book:Sbook?){

    Card(modifier = Modifier
        .padding(9.dp)
        .clickable { }
        .clip(RoundedCornerShape(20.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Row(horizontalArrangement = Arrangement.Start) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data( if(book?.photoUrl.isNullOrEmpty())
                        "https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg"
                        else
                        book?.photoUrl
                    )
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.dummy),
                contentDescription = "image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(140.dp)
                    .width(100.dp)
                    .padding(10.dp)

            )

            Column(modifier = Modifier.padding(5.dp)) {
                Text(
                    text = book?.title ?: "No Title",
                    //text = "Hotel Transalvaniya",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(2.dp),
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "Author: ${book?.authors?: "Unknown"}",
                    //text = "book author",
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "Published Date: ${book?.publishedDate?:"khaiii,Kei na kei ta ho"}",
                    //text = "2004/06/24",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(5.dp))
            }
        }
    }
}


//making text view to enter thoughts
@Composable
fun ShowSimpleForm(bookinfo:Sbook,navController: NavController){

    //thought-> is a string we are going to need a state of the note so let's go

    val notesText = remember {
        mutableStateOf("")
    }

    SimpleForm(defaultvalue = if(bookinfo.notes.toString().isNotEmpty())
        bookinfo.notes.toString()
        else
        "You haven't shared your thoughts yet \uD83D\uDE2D"){thought-> //it is a string returned from text Box
        notesText.value=thought

    }

}


@Composable
fun SimpleForm(
    modifier: Modifier=Modifier,
    loading:Boolean=false,
    defaultvalue:String="Great Book",
    onSearch: (String) -> Unit={}
){
    val textFieldValue = rememberSaveable{
        mutableStateOf(defaultvalue)
    }

    val keyboardController = LocalSoftwareKeyboardController.current

        //checking whether it's valid or not
    val valid = remember(textFieldValue.value) {
        textFieldValue.value.trim().isNotEmpty() //if something is written in text field it will show true
    }

    Input(
        maxlines = 5,
        valueState = textFieldValue,
        labelId = "Enter your thoughts",
        enabled = true,
        modifier=Modifier
            .fillMaxWidth()
            .padding(3.dp),
        keyboardType = KeyboardType.Unspecified,
        imeaction = ImeAction.Done,
        onAction = KeyboardActions {
            if(!valid)
                return@KeyboardActions

            onSearch(textFieldValue.value.trim()) //this will pass as lamda expression
            // to the calling function i.e the thing written inside

            keyboardController?.hide()
        },
        isSingleLine = false
    )


}