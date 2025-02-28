package com.ptk.healthflow.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatTimestamp(timestamp: Long): String {
    // Convert Unix timestamp to Date
    val date = Date(timestamp * 1000) // Multiply by 1000 to convert to milliseconds

    // Create a SimpleDateFormat instance with desired format
    val sdf = SimpleDateFormat("hh:mm a (dd/MM/yyyy)", Locale.getDefault())

    // Format the Date object
    return sdf.format(date)
}