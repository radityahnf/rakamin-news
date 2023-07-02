package com.example.news

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

class Util {
    companion object{
        fun formatDate(date : String) : String{
            val formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
            val zonedDateTime = ZonedDateTime.parse(date, formatter)

            val customFormatter = DateTimeFormatterBuilder()
                .appendPattern("dd-MM-yyyy")
                .toFormatter()

            return zonedDateTime.format(customFormatter)
        }
    }
}