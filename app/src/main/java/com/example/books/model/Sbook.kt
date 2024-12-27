package com.example.books.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName

data class Sbook(
    @Exclude
    var id:String?=null, //for firestore to work this properties must be var
    var title:String?=null,
    var authors:String?=null,
    var notes:String?=null,

    @get:PropertyName("photo_url") //by writing this it will be saved as photo_url in database
    @set:PropertyName("photo_url")
    var photoUrl:String?=null,
    var category: String?=null,

    @get:PropertyName("published_date")
    @set:PropertyName("published_date")
    var publishedDate:String?=null, //in data base it should save as publish_date

    var rating:Double?=null,
    var descrption:String?=null,

    @get:PropertyName("stared_Reading")
    @set:PropertyName("stared_Reading")
    var StaredReading:Timestamp?=null,

    @get:PropertyName("finished_reading_at")
    @set:PropertyName("finished_reading_at")
    var finishedReading:Timestamp?=null,

    @get:PropertyName("user_id")
    @set:PropertyName("user_id")
    var userId:String?=null, //we can identify which user added this

    @get:PropertyName("google_book_id")
    @set:PropertyName("google_book_id")
    var googleBookId:String?=null,

    @get:PropertyName("page_count")
    @set:PropertyName("page_count")
    var pagecount:String?= null
)