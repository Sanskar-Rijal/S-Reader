package com.example.books.Screens.home

import android.provider.ContactsContract.Data
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.data.DataorException
import com.example.books.model.Sbook
import com.example.books.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewmodel @Inject constructor(private val repository:FirebaseRepository) :ViewModel() {

    var list :List<Sbook> by mutableStateOf(listOf())
    var isloading:Boolean by mutableStateOf(false)

    init {
        getallBooksFromDatabase()
    }
    private fun getallBooksFromDatabase(){
        viewModelScope.launch(Dispatchers.IO) {
            isloading=true
            var response = repository.getAllBooksFromDataBase()
            Log.d("ramesh", "homeviewmodel: ${response} ")
            when(response){
                is DataorException.Success->{
                    list=response.data!!
                    isloading=false
                    Log.d("leon", "getallBooksFromDatabase: ${response.data}")
                }
                is DataorException.Loading ->{
                    Log.d("fire", "getallBooksFromDatabase: ${response.message}")
                    isloading=false
                }
                is DataorException.Error->{
                    Log.d("fire", "getallBooksFromDatabase: ${response.message}")
                    isloading=false
                }
                else ->{
                    isloading=false
                }
            }
        }
    }

}