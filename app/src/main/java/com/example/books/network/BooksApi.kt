package com.example.books.network

import com.example.books.model.Book
import com.example.books.model.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

//think of this as DAO it connect's to retrofit

@Singleton
interface BooksApi {
    //now we use get post etc

    /**
     *     https://www.googleapis.com/books/v1/volumes?q=android
     *     in value we pass every thing before QUERY
     *     here we have to pass "volumes" we are appending the path previously we had stored
     *     https://www.googleapis.com/books/v1/ right? so we are adding volume behind
     */
    @GET(value ="volumes")
    suspend fun getAllBook(
        //now we will pass the Query q=... will be a name of book which is of type String
        @Query(value = "q") book:String):Book
    //remember? we created a class called Book using Json file which is the main class , json file willl return BOOK


    @GET(value = "volumes/{bookId}")
    suspend fun getBookInfo(@Path("bookID")bookId:String):Item //inside path the name should be same as above volumes/{.samename.}

    /**
     * The @Path("bookID") annotation in Retrofit
     * is used to replace the {bookId} placeholder in the endpoint with an actual value.
     * examples
     * val bookId = "abcd1234"
     * api.getBookInfo(bookId)
     *https://www.googleapis.com/books/v1/volumes/abcd1234
     */


}