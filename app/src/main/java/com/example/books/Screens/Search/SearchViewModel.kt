package com.example.books.Screens.Search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.query
import com.example.books.data.DataorException
import com.example.books.model.Item
import com.example.books.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: BooksRepository):ViewModel(){

    var list:List<Item> by mutableStateOf(listOf()) //creating a variable that is of correcttype

    init {
        loadBooks()
    }

    private fun loadBooks(){
        SearchBooks("Android")
    }

    private fun SearchBooks(name:String){
        viewModelScope.launch(Dispatchers.IO) {
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
                        Log.d("data", "Books received from api $list")
                    }
                    is DataorException.Error->{
                        Log.d("api", "SearchBooks:Failed Getting The Books ")
                    }
                    else->{

                    }
                }
            }catch (ex:Exception){
                Log.d("api", "SearchBooks: ${ex.message.toString()} ")
        }

                    }
    }


}