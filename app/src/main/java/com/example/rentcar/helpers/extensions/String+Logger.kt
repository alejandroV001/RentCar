package com.example.rentcar.helpers.extensions

import android.util.Log

fun String.logErrorMessage(tag: String = "MyApp") {
    Log.e(tag, this)
}