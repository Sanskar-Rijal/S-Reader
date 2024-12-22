package com.example.books.repository

import android.provider.ContactsContract.Data
import com.example.books.data.DataorException
import com.example.books.model.Item
import com.example.books.network.BooksApi
import javax.inject.Inject

//we inject weather_api so that we can access the data
class BooksRepository @Inject constructor(
    private val  booksApi: BooksApi){


    suspend fun getBooks(searchBookQuery:String):DataorException<List<Item>>{ //we are getting :List<Item>
       return try {
            //since it's loading first so
            DataorException.Loading(data =true)
            val itemList =booksApi.getAllBook(searchBookQuery).items //we are getting list of all the books

           if(itemList.isNotEmpty()){
               DataorException.Loading(data = false)
           }

            DataorException.Success(data = itemList)

        }catch(ex:Exception){
           DataorException.Error(message = ex.message.toString())
        }
    }

    //geting book by id
    suspend fun getBookInfo(bookId:String):DataorException<Item>{
         val response= try {
             DataorException.Loading(data = true)
             booksApi.getBookInfo(bookId)
         }catch (ex:Exception){
            return DataorException.Error(message ="An Error Occured ${ex.message.toString()}")
         }
        DataorException.Loading(data = false)
        return DataorException.Success(data = response)
    }
}