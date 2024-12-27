package com.example.books.repository

import android.provider.ContactsContract.Data
import android.util.Log
import com.example.books.data.DataorException
import com.example.books.model.Sbook
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val queryBook :Query) {

    suspend fun getAllBooksFromDataBase():DataorException<List<Sbook>>{

       return try {
           DataorException.Loading(data = true)
           val itemsList = queryBook.get().await().documents.map {documentSnapshot ->
               documentSnapshot.toObject(Sbook::class.java)!!
           }
           if(itemsList.isNotEmpty()){
               DataorException.Loading(data = false)
           }
           Log.d("ramesh", "getAllBooksFromDataBase: ${itemsList} ")
           DataorException.Success(data = itemsList)

        }catch (ex:FirebaseFirestoreException){
            DataorException.Error(message = ex.message.toString())
        }

    }

}