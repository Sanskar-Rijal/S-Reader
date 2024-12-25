package com.example.books.Screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.books.components.TitleSection
import com.example.books.model.Sbook
import com.example.books.navigation.ReaderScreens
import com.example.books.widgets.AppBarbysans
import com.example.books.widgets.ListCard
import com.google.firebase.auth.FirebaseAuth


@Composable
fun Home(navController: NavController){
    Scaffold(
        topBar = {
            AppBarbysans(title = "S. Reader", navController = navController)
        },
        floatingActionButton = {
            FloatingContent {
                navController.navigate(ReaderScreens.SearchScreen.name)
            }
        }){contentpadding->
        Surface(modifier = Modifier
            .padding(contentpadding)
            .fillMaxSize()) {

            HomeContent(navController)
        }

    }
}

//floating bottom below
@Composable
fun FloatingContent(onClick:(String)->Unit ){
    FloatingActionButton(onClick={
        onClick("")
    },
        shape = RoundedCornerShape(45.dp),
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
    ) {
        Icon(Icons.Default.Add, contentDescription = "Add Icon", tint = MaterialTheme.colorScheme.onTertiaryContainer)
    }
}


//contenetforhome Section
@Composable
fun HomeContent(navController: NavController){

    //checking Card view
    val listofbook:List<Sbook> = listOf(
        Sbook(id="sdfasf", title = "sdfasfa", authors = "ahsdfasf", notes = "asdfafs"),
        Sbook(id="sdfasf", title = "wer", authors = "fdsa", notes = "fwef"),
        Sbook(id="adsf", title = "dsf", authors = "dvdcd", notes = "sfs"),
        Sbook(id="dfg", title = "43re", authors = "ahsdfasf", notes = "ewrwe"),
        Sbook(id="sdfwerasf", title = "sdfsa", authors = "sdfa", notes = "vcbdvb")
    )

    //getting current user form firebase Auth
    val currentUserName=if(!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
        FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0)
    }else{
        "N/A"
    }


    Column(modifier = Modifier
        .padding(4.dp),
        verticalArrangement = Arrangement.Top) {


        Row(modifier = Modifier
            .fillMaxWidth()
          //  .align(alignment = Alignment.Start),
           // ,horizontalArrangement = Arrangement.SpaceBetween,
            ,verticalAlignment = Alignment.CenterVertically) {

            TitleSection(label = "Your reading \n" +"activity right now....")

            Spacer(modifier = Modifier.fillMaxWidth(0.3f))

           Column(horizontalAlignment = Alignment.CenterHorizontally) {
                //profile Icon
                Icon(imageVector = Icons.Default.AccountCircle,
                    modifier = Modifier.clickable {
                        //something to do
                        navController.navigate(ReaderScreens.StatsScreen.name)
                    }
                        .size(50.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    contentDescription = "icon")
                    //UserName
                Text(text = currentUserName!!,
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 17.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip)
                HorizontalDivider()
            }

        }
        ListCard(book = listofbook[0])

        Spacer(modifier = Modifier.height(15.dp))

        //creating Reading List
        ReadingRightNowArea(books = listofbook,navController=navController)

    }
}


@Composable
fun ReadingRightNowArea(books:List<Sbook>, navController: NavController){
    TitleSection(label ="Reading List")
    //making scrollable Row to show currently reading Books
    Spacer(modifier = Modifier.height(15.dp))
    BookListArea(listofBooks =books, navController = navController){
        //something to do when the card is clicked, i.e go to details screen

    }
}

@Composable
fun BookListArea(listofBooks:List<Sbook>,navController: NavController,onCardPressed:(String)->Unit){

    val scrollableState = rememberScrollState()

    LazyRow(modifier = Modifier
        .fillMaxSize()
       // .horizontalScroll(scrollableState)
        .heightIn(280.dp)
    ) {
        items(items = listofBooks){book->
            Spacer(modifier = Modifier.width(7.dp))
        ListCard(book = book){//"it" will be a string passed in lamda i.e twilight
            onCardPressed(it)
        }
        }
    }


}




