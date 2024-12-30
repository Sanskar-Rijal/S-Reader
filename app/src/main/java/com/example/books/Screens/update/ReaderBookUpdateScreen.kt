package com.example.books.Screens.update

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.books.components.StylishButton
import com.example.books.model.Book
import com.example.books.model.Sbook
import com.example.books.navigation.ReaderScreens
import com.example.books.utils.formatDate
import com.example.books.widgets.AppBarbysans
import com.example.books.widgets.CustomAlertDialouge
import com.example.books.widgets.Star
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
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
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

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
    //to show toast

    val context:Context= LocalContext.current

    //thought-> is a string we are going to need a state of the note so let's go

    val notesText = remember {
        mutableStateOf(bookinfo.notes)
    }

    val isStartedReading = remember {
        mutableStateOf(false)
    }

    val isFinishedReading = remember {
        mutableStateOf(false)
    }

    val ratingVal = remember {
        mutableStateOf(bookinfo.rating)
    }


    SimpleForm(defaultvalue = if(bookinfo.notes.toString().isNotEmpty())
        bookinfo.notes.toString()
        else
        "You haven't shared your thoughts yet \uD83D\uDE2D"){thought-> //it is a string returned from text Box
        notesText.value=thought
    }

    Row(modifier = Modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start) {

        TextButton(
            onClick = {
                //to do When the Text are clicked i have to change the text from "start Reading" to "started Reading"
                isStartedReading.value=true
            },
            enabled = bookinfo.StaredReading == null
        ) {
            if (bookinfo.StaredReading == null) {
                if (!isStartedReading.value) {
                    Text(
                        text = "Start Reading",
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    Text(
                        text = "Started Reading",
                        color = Color.Red.copy(0.4f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

            }else{
                Text(text = "Started on: ${formatDate(bookinfo.StaredReading!!)}",
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    style = MaterialTheme.typography.bodyLarge)
            }
}
            Spacer(modifier = Modifier.width(4.dp))

            TextButton(onClick = {
                isFinishedReading.value=true
            },
                enabled = bookinfo.finishedReading==null) {
                if(bookinfo.finishedReading==null){
                    if(!isFinishedReading.value){
                        Text(text = "Mark as Read",
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                            style = MaterialTheme.typography.bodyLarge)
                    }else{
                        Text(text = "Finished Reading!!",
                            color = Color.Red.copy(0.4f),
                            style = MaterialTheme.typography.bodyLarge)
                    }
                }else{
                    Text(text = "Finished on: ${
                        formatDate(bookinfo.finishedReading!!)}",
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        style = MaterialTheme.typography.bodyLarge)
                }
            }
        //outside of the text

    }
    //outside of row

    Text(text = "Rating",
        modifier = Modifier.padding(bottom = 3.dp))
    bookinfo.rating?.toInt().let {rating->

        Star(rating = rating!!){ratedval->
            ratingVal.value=ratedval
        }
    }

    Spacer(modifier = Modifier.height(15.dp))

    Row(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly) {

        //making sure during update there is a text in the text field
        val changedNotes= bookinfo.notes != notesText.value
        val changedRating = bookinfo.rating?.toInt() != ratingVal.value
        val isFinishedTimeStamp = if(isFinishedReading.value) Timestamp.now() else bookinfo.finishedReading
        val isStartedTimeStamp = if(isStartedReading.value) Timestamp.now() else bookinfo.StaredReading


        val bookUpdate = changedNotes || changedRating || isStartedReading.value  || isFinishedReading.value //if somethiing one of it is true we have to update the value

        val bookToUpdate = hashMapOf(
            "finished_reading_at" to isFinishedTimeStamp,
            "stared_Reading" to isStartedTimeStamp,
            "rating" to ratingVal.value,
            "notes" to notesText.value
        ).toMap()

        //for alert dialouge
        var openAlertDialouge by remember {
            mutableStateOf(false)
        }

        StylishButton(label = "Delete") {
            openAlertDialouge=true
        }
        if(openAlertDialouge){
            CustomAlertDialouge(
                icon = Icons.Default.Warning,
                dialogTitle = "Delete Book",
                dialogText = "Are you sure? Action can't be undone",
                onConfirmation = {
                    FirebaseFirestore.getInstance().collection("books")//now i have to get into the document
                        .document(bookinfo.id!!)
                        .delete()
                        .addOnCompleteListener{
                            if(it.isSuccessful){
                                openAlertDialouge=false
                                navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                            }
                        }
                },
                onDismissRequest ={
                    openAlertDialouge=false
                }
            )
        }

        StylishButton(label = "Update") {
            if(bookUpdate){
                FirebaseFirestore.getInstance().collection("books")//once we get to the collection we need to get to the actual book to update it
                    .document(bookinfo.id!!)
                    .update(bookToUpdate)
                    .addOnCompleteListener{task->
                        Toast.makeText(context,"Book updated Successfully \uD83D\uDE1C",Toast.LENGTH_SHORT).show()
                        navController.navigate(ReaderScreens.ReaderHomeScreen.name)

                    }.addOnFailureListener{
                        //kei na kei ta garna parxa hehehhee
                    }
            }else{
                Toast.makeText(context,"Noting is changed what shall i update? \uD83E\uDD26\u200D♀\uFE0F",Toast.LENGTH_SHORT).show()
            }
        }
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
        modifier= Modifier
            .fillMaxWidth()
            .padding(3.dp),
        keyboardType = KeyboardType.Unspecified,
        imeaction = ImeAction.Done,
        onAction = KeyboardActions {
            if(!valid)
                return@KeyboardActions

            onSearch(textFieldValue.value.trim()) //this will pass as lamda expression
            // to the calling function i.e the thing written inside

            Log.d("vampitre", "SimpleForm: ${textFieldValue.value} ")

            keyboardController?.hide()
        },
        isSingleLine = false
    )

}