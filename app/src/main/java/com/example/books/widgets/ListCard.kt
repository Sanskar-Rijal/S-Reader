package com.example.books.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.books.R
import com.example.books.model.Book
import com.example.books.model.Item
import com.example.books.model.Sbook
import com.example.books.navigation.ReaderScreens


@Composable
fun ListCard(
    book:Sbook=Sbook(id="asdf", title = "Running", authors = "Me and you ", notes = "sans is pro"),
    onPressDetails:(String) -> Unit={}){ //on pressed will return the id of book

    //creating context about this composable
    val context= LocalContext.current
    val resources=context.resources
    //for making dynamic width for column we create a variable
    val displayMetrics = resources.displayMetrics
    //now getting the screen width
    val screenWidth=displayMetrics.widthPixels/displayMetrics.density //it will get the width of the screen

    val spacing=10.dp

    Card(modifier = Modifier
        .height(250.dp)
        .width(208.dp)
        .padding(10.dp)
        .clickable {
            book.id?.let { onPressDetails.invoke(it) }
        },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {

        Column(modifier = Modifier
            .padding(3.dp)
            .width(screenWidth.dp-(spacing*2)),
            horizontalAlignment = Alignment.Start
            ) {//making the width of the column dynamic depending on the content inside it

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center){
               // loading image from internet
                AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                    .data("https://books.google.com/books/content?id=EKV6zgEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api")
                    .crossfade(true)
                    .build(),
                    placeholder= painterResource(R.drawable.dummy),
                    contentDescription = book.title,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .height(140.dp)
                        .width(100.dp)
                        .padding(4.dp))
                Spacer(Modifier.width(20.dp))

                Column(modifier = Modifier.padding(top = 10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    Icon(imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = "icon",
                        modifier = Modifier.padding(bottom = 2.dp))

                    BookRatingScore(3.5)
                }

            }
            //outside of row
            book.title?.let {
                Text(text = it,
                    modifier = Modifier.padding(4.dp),
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Clip,
                    color = MaterialTheme.colorScheme.onBackground)
            }// if we have more text that can't be displayed it will be clipped

            book.authors?.let {
                Text(text= it,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
           Row(modifier = Modifier.fillMaxSize(),
               horizontalArrangement = Arrangement.End,
               verticalAlignment = Alignment.Bottom) {
               roundedButton(label = "Reading", radius = 70)

       }
    }
}

//creating composable for book rating
@Composable
fun BookRatingScore(rating:Double=4.5){
    Surface(modifier =  Modifier
        .height(70.dp)
        .padding(4.dp),
        shape = RoundedCornerShape(50.dp),
        shadowElevation = 6.dp,
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            Icon(imageVector = Icons.Filled.StarBorder,
                contentDescription = "Rating",
                modifier = Modifier.padding(2.dp))

            Text(text = rating.toString(),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(start = 4.dp))

        }
    }
}

//creating another Composabel to show  whether user is reading the book or not
@Composable
fun roundedButton(
    label:String="Reading",
    radius:Int=30,
    onPress:()->Unit ={}
){
    Surface(modifier = Modifier
        .clip(RoundedCornerShape(bottomEndPercent = 40,
            topStartPercent = radius)),
        color = MaterialTheme.colorScheme.tertiaryContainer){
        Column(modifier = Modifier
            .width(90.dp)
            .heightIn(60.dp)
            .clickable {
                onPress.invoke()
            },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
            ) { //heightin means it will not be smaller
        // than minimum and not be greater than maximum
            /**
             * If the content naturally requires less space than the min height, it will expand to meet the min height.
             * If the content requires more space than the max height, it will shrink to fit within the max height.
             * If the content naturally fits within the range, it will take its natural height.
             */
            Text(text = label,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}


@Composable
fun SearchListCard(
    navController: NavController,
    book:Item,
    onPressDetails:(String) -> Unit={}){
    Card(modifier = Modifier
        //.height(115.dp)
        .fillMaxWidth()
        .padding(10.dp)
        .clickable {
            onPressDetails.invoke(book.id)
        },
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {

        Row(modifier = Modifier
            .fillMaxSize()
            .padding(3.dp),
            verticalAlignment = Alignment.Top) {
            AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                .data(if(book.volumeInfo.imageLinks.smallThumbnail.isEmpty())
                    "https://books.google.com/books/content?id=EKV6zgEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api"
                else
            book.volumeInfo.imageLinks.smallThumbnail)
                .crossfade(true)
                .build(),
                placeholder= painterResource(R.drawable.dummy),
                contentDescription ="hehehe",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(120.dp)
                    .width(90.dp)
                    .padding(4.dp))
            Spacer(Modifier.width(20.dp))

            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start) {
                book.volumeInfo.title.let {
                    Text(text = it,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Clip,
                        color = MaterialTheme.colorScheme.onBackground)
                }// if we have more text that can't be displayed it will be clipped

                book.volumeInfo.authors.let {
                    Text(text="Author: $it ",
                        modifier = Modifier.padding(2.dp),
                        style = MaterialTheme.typography.bodySmall,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Text(text="Date: ${book.volumeInfo.publishedDate}",
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(text="${book.volumeInfo.categories}",
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

    }
}