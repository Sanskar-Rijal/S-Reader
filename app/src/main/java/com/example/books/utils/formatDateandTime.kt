package com.example.books.utils

import com.google.firebase.Timestamp
import java.text.DateFormat


fun formatDate(timeStamp:Timestamp):String{

    val date = DateFormat.getDateInstance()
        .format(timeStamp.toDate()).toString()
        .split(",") [0] // march 12,2023 i want to get read of year so we are passing split

    return date

}