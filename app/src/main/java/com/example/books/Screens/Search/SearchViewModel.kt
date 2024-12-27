package com.example.books.Screens.Search

import android.provider.ContactsContract.Data
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.query
import com.example.books.data.BookEvent
import com.example.books.data.DataorException
import com.example.books.data.EVENT
import com.example.books.model.Item
import com.example.books.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: BooksRepository):ViewModel(){

    //for book not found
    val popUpNotification = mutableStateOf<BookEvent<String>?>(null)

    var list:List<Item> by mutableStateOf(listOf()) //creating a variable that is of correcttype
    var isLoading:Boolean by mutableStateOf(true)

    init {
        loadBooks()
    }

    private fun loadBooks(){
        SearchBooks("twilight")
    }

     fun SearchBooks(name:String){
        viewModelScope.launch(Dispatchers.IO) {
           // isLoading=true in beggining it's true so we don't need this
            //checking if the name is empty or not
            if(name.isEmpty()){
                return@launch
            }
            try {
                val response=repository.getBooks(name) //we are getting response from our Repository i.e DataorException class
                //now we use when for handeling loading, success , error
                when(response){
                    is DataorException.Success->{
                        //every thing is successful so we have some data
                        list=response.data!!
                        //after we get the data loading==fasle
                        if(list.isNotEmpty()){
                            isLoading=false
                        }
                        Log.d("data", "Books received from api $list")
                    }
                    is DataorException.Error->{
                        isLoading=false
                        Log.d("samri", "SearchBooks:Failed Getting The Books ${response.message} ")
                        handleException(CustomMsg = "Book Not Found")

                    }
                    else->{
                        isLoading=false
                    }
                }
            }catch (ex:Exception){
                Log.d("heheh", "SearchBooks: ${ex.message.toString()} ")
        }

        }
    }
    private fun handleException(exception: Exception?=null,CustomMsg:String=""){

        exception?.printStackTrace() //printStactTrace helps to print the exact error

        val errorMessage = exception?.localizedMessage?:" "

        Log.d("june", "handleException is called")
        /**
         * If exception is not null, it retrieves the localizedMessage (a human-readable description of the exception).
         * If exception is null, the result is null.
         * using Elvis operator , ternary operator
         */
        val message = if(CustomMsg.isEmpty()) errorMessage else "$CustomMsg $errorMessage"
        popUpNotification.value = BookEvent(message)
    }

}