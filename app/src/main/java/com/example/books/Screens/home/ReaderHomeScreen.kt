package com.example.books.Screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CompareArrows
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
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



@Composable
fun ReadingRightNowArea(books:List<Sbook>, navController: NavController){

}




//contenetforhome Section
@Composable
fun HomeContent(navController: NavController){

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
        ListCard()
    }
}