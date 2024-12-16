
  package com.example.books

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.books.ui.theme.BooksTheme
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

  @AndroidEntryPoint
  class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            myApp {
                val database:FirebaseFirestore=FirebaseFirestore.getInstance()
                val user:MutableMap<String,Any > = HashMap()
                user["FirstName"]="Sans"
                user["LastName"]="Rijal"
                database.collection("users")
                    .add(user)
                    .addOnSuccessListener {
                        Log.d("FB", "onCreate:${it.id} ")
                    }
                    .addOnFailureListener{
                        Log.d("FB", "onCreate: $it ")
                    }
            }
        }
    }
}
  @Composable
  fun myApp(content : @Composable () -> Unit ){
      BooksTheme {
          content()
      }
  }

