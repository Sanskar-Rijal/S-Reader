package com.example.books.utils

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(timeStamp: Timestamp): String {
    val sdf = SimpleDateFormat("MMM dd", Locale.getDefault()) // Excludes the year
    return sdf.format(timeStamp.toDate())
}
