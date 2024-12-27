package com.example.books.Screens.details

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.books.data.DataorException
import com.example.books.model.Item
import com.example.books.navigation.ReaderScreens
import com.example.books.widgets.AppBarbysans


@Composable
fun BookDetailScreen(navController: NavController,
                     bookId:String="hehehehe",
                     viewModel: DetailsViewModel= hiltViewModel()
) {
    Scaffold(
        topBar = {
            AppBarbysans(
                title = "Book Details",
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                showProfile = false,
                navController = navController
            ) {
                navController.navigate(ReaderScreens.SearchScreen.name)
            }
        }
    ) { contentpadding ->
        Surface(
            modifier = Modifier
                .padding(contentpadding)
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier.padding(top = 12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
/** we can use this also if we use return repository.getbookinfo in viewmodel
                val bookInfo = produceState<DataorException<Item>>(initialValue = DataorException.Loading()){
                    value = viewModel.getbookInfo(bookId = bookId)
                }.value
               // val data= viewModel.getbookInfo(bookId = bookId)
                Log.d("samsth", "BookDetailScreen: ${bookInfo.data.toString()}")
                if(bookInfo.data !=null){
                    Text(text = "hi details are ${bookInfo.data.volumeInfo.title}")
                }
**/
                // Trigger the fetch when the screen is loaded
                LaunchedEffect(key1 = bookId) {
    viewModel.FetchBooks(bookId)
}
                val bookInfo= viewModel.bookInfo
                if(bookInfo ==null){
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }else
                {
                    Text(text = bookInfo.volumeInfo.description)
                }
            }
        }
    }
}