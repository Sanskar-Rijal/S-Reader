package com.example.books.data

open class EVENT<out T>(private val content:T){
    //it will check whether the event are handled or note
    var hasBeenHandled=false
        private set
    fun getContentorNull():T?{
        return if(hasBeenHandled){
            null
        }
        else{
            hasBeenHandled=true
            content
        }
    }
}