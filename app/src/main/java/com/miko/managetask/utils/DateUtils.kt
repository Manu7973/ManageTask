package com.miko.managetask.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// DateUtils class is used to perform date specific operations.
object DateUtils {

    fun getCurrentDate(): String{ //returns current date in dd/MM/yyyy format
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val today = sdf.format(Date())
        return today
    }

    fun getFormattedDate(date: String): String { //returns date in MMM dd format
        val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd", Locale.getDefault())

        val dueDateText = try {
            val date = inputFormat.parse(date)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            return ""
        }

        return dueDateText
    }
}