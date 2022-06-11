package com.example.telgraf.Utils.TimeFunctions

import android.icu.text.SimpleDateFormat
import android.util.Log
import java.util.*

class DateTest {

    private val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.ENGLISH)

    private var currentDate = simpleDateFormat.format(Date())

    private var expDate: String

    var compareResult: Int? = null


    constructor(expDate: String) {
        this.expDate = getDateString(expDate.toInt())
        Log.d("EXP",this.expDate.toString())
    }


    fun getDateString(time: Long): String = simpleDateFormat.format(time * 1000L)

    fun getDateString(time: Int): String = simpleDateFormat.format(time * 1000L)

    fun getCurrentDate(): String = currentDate

    fun compareDates() {

        val cmp = expDate.compareTo(currentDate)
        when {
            cmp > 0 -> {
                Log.d("${expDate} is after ${currentDate}","")
                compareResult = 1
            }
            cmp < 0 -> {
                Log.d("${expDate} is before ${currentDate}","")
                compareResult = -1
            }
            else -> {
                compareResult = 0
            }
        }
    }

}