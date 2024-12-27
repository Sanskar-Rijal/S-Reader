package com.example.books.di

import com.example.books.network.BooksApi
import com.example.books.repository.FirebaseRepository
import com.example.books.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun ProvideBookAPI():BooksApi {
        return Retrofit.Builder()
            .baseUrl(Constants.Base_Url)
            .addConverterFactory(GsonConverterFactory.create()) //converting into json objects
            .build()
            .create(BooksApi::class.java)
    }//Now this will go and get book from NETWORK


    @Singleton
    @Provides
    fun provideFireBookRepository()
    =FirebaseRepository(queryBook = FirebaseFirestore.getInstance().collection("books"))

}