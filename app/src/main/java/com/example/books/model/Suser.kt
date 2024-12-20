package com.example.books.model

data class Suser(
    val id:String?,
    val userId:String,
    val displayName:String,
    val profileIcon:String,
    val quote:String,
    val proffession:String
){
    //we have to create users so
    fun toMap():MutableMap<String,Any?>{
        return mutableMapOf(
            "user_Id" to this.id,
            "display_Name" to this.displayName,
            "quote" to this.quote,
            "proffession" to this.proffession,
            "avatar" to this.profileIcon
        )
    }
}
