package com.example.books.model

data class Sbook(
    var id:String?=null, //for firestore to work this properties must be var
    var title:String?=null,
    var authors:String?=null,
    var notes:String?=null
)