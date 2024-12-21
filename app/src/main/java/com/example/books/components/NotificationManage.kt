package com.example.books.components

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.books.Screens.login.LoginViewModel

@Composable
fun NotificationMessage(viewModel: LoginViewModel= viewModel()){
    val notificationState= viewModel.popUpNotification.value
    val notifmsg=notificationState?.getContentorNull()
    //now we display toast message
    val contex:Context= LocalContext.current
    Log.d("april", "entered toast but outside if block ")
          if(notifmsg != null){
        Toast.makeText(contex, notifmsg, Toast.LENGTH_SHORT).show()
        Log.d("april", "entered toast: ")
    }
}