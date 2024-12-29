package com.example.books.widgets

import android.view.MotionEvent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.books.R

@OptIn(ExperimentalComposeUiApi::class)

@Composable
fun Star(
    modifier: Modifier=Modifier,
    rating:Int,
    onPressRating:(Int)->Unit={}
) {
    var ratingState by remember {
        mutableStateOf(rating)
    }

    var selected by remember {
        mutableStateOf(false)
    }

    val size by animateDpAsState(
        targetValue = if(selected) 42.dp else 34.dp,
        spring(Spring.DampingRatioMediumBouncy)
    )

    Row(
        modifier = modifier.width(280.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center){

        for ( j in 1..5){
            Icon(painter = painterResource(R.drawable.rating) ,
                contentDescription = "Rating",
                modifier = modifier
                    .width(size)
                    .height(size)
                    .pointerInteropFilter {
                        when(it.action){
                            MotionEvent.ACTION_DOWN -> { //when we tap on the star
                                selected = true
                                onPressRating(j)
                                ratingState = j
                            }
                            MotionEvent.ACTION_UP -> { //when we stop tapping the star then it will execute
                                selected = false
                            }
                        }
                        true //passing true because we want to get out of it
                    },
                tint = if (j <= ratingState) Color(0xFFFFD700) else Color(0xFFA2ADB1)
            )
        }


    }

}