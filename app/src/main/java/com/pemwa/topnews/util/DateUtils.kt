package com.pemwa.topnews.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

class DateUtils {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun convertToSimpleDateFormat(date: String): String {
            return SimpleDateFormat("yyyy-MM-dd").format(
                SimpleDateFormat("yyyy-MM-dd").parse(date)
            )
        }
    }
}