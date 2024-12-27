package com.example.books.Screens.details



import android.provider.ContactsContract.Data
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.books.data.DataorException
import com.example.books.model.Item
import com.example.books.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: BooksRepository):ViewModel() {

//    suspend fun getbookInfo(bookId: String): DataorException<Item> {
//        Log.d("flower", "getbookInfo: ${repository} ")
//        return repository.getBookInfo(bookId)
//    }
var bookInfo:Item? by mutableStateOf(null)
    var isLoading:Boolean by mutableStateOf(true)

    fun FetchBooks(id:String){
        viewModelScope.launch(Dispatchers.IO){
            if(id.isEmpty()){
                return@launch
            }
            try {
                val response =repository.getBookInfo(id)
                when (response){
                    is DataorException.Success ->{
                        bookInfo=response.data!!
                        if(bookInfo != null){
                            isLoading=false
                        }
                    }
                    is DataorException.Error ->{
                        isLoading=false
                        Log.d("sansi", "FetchBooks:${response.message} ")
                    }
                    else ->{
                        isLoading=false
                    }
                }
            }catch (ex:Exception){
                Log.d("sansi", "catch block: ${ex.message.toString()} ")
            }
        }
    }



}