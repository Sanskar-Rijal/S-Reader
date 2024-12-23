package com.example.books.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.books.R
import com.example.books.model.Book

@Preview
@Composable
fun ListCard(onPressDetails:(String) -> Unit={}){

    //creating context about this composable
    val context= LocalContext.current
    val resources=context.resources
    //for making dynamic width for column we create a variable
    val displayMetrics = resources.displayMetrics
    //now getting the screen width
    val screenWidth=displayMetrics.widthPixels/displayMetrics.density //it will get the width of the screen

    val spacing=10.dp

    Card(modifier = Modifier
        .height(256.dp)
        .width(210.dp)
        .padding(19.dp)
        .clickable {
            onPressDetails.invoke("Twilight")
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
                    .data("")
                    .crossfade(true)
                    .build(),
                    placeholder= painterResource(R.drawable.dummy),
                    contentDescription = "Image for Book",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .height(140.dp)
                        .width(100.dp)
                        .padding(4.dp)
                        .clip(CircleShape)
                )
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
            Text(text = "Book Title",
                modifier = Modifier.padding(4.dp),
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Clip)// if we have more text that can't be displayed it will be clipped

            Text(text="Authors:All...",
                style = MaterialTheme.typography.bodySmall
            )
        }
           Row(modifier = Modifier.fillMaxWidth(),
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
@Preview
fun roundedButton(
    label:String="Reading",
    radius:Int=30,
    onPress:()->Unit ={}
){
    Surface(modifier = Modifier
        .clip(RoundedCornerShape(bottomEndPercent = radius,
            topStartPercent = radius)),
        color = MaterialTheme.colorScheme.tertiaryContainer){
        Column(modifier = Modifier
            .width(98.dp)
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
            Text(text = "Label",
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}