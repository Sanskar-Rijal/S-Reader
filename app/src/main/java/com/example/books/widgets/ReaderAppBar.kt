package com.example.books.widgets

import androidx.collection.emptyLongSet
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.books.R
import com.example.books.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarbysans(title:String,
                 showProfile:Boolean=true,
                 navController: NavController,
                 icon:ImageVector?=null,
                 onBackArrowClicked:()->Unit={}){
    CenterAlignedTopAppBar(title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if(showProfile){
                Image(painter = painterResource(R.drawable.reading), contentDescription = "icon",
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .size(35.dp))
            }
            Text(text = title,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = TextStyle(fontWeight = FontWeight.Bold,
                    fontSize = 23.sp)
            )
        }
    },
        navigationIcon = {
            if(icon!=null){
                IconButton(onClick = {
                    onBackArrowClicked.invoke()
                }){
                    Icon(imageVector = icon,
                        contentDescription = "back arrow",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
        }
        ,
        actions = {
            //actions are displayed at the end of topbar
            if(showProfile){
                IconButton(onClick = {
                    FirebaseAuth.getInstance().signOut().run {
                        navController.navigate(ReaderScreens.LoginScreen.name)
                    }}){
                    Icon(imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = "LogOut",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f))
                    }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primaryContainer)
    )
}