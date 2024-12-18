package com.example.books.Screens.login

import android.net.http.UrlRequest.Status

data class LoadingState(
    val status:Status,
    val message:String?=null
){

    companion object{
        val IDLE = LoadingState(Status.IDLE)
        val FAILED= LoadingState(Status.FAILIED)
        val SUCCESS= LoadingState(Status.SUCCESS)
        val LOADING = LoadingState(Status.LOADING)
    }
    
    enum class Status{
        SUCCESS,
        FAILIED,
        LOADING,
        IDLE
    }
}