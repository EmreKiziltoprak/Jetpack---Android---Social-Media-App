package com.example.telgraf.Network.JWTDECODER

import android.util.Base64
import android.util.Log
import java.io.UnsupportedEncodingException

object JWTUtils {
    @Throws(Exception::class)
    fun decoded(JWTEncoded: String) {
        Log.d("JWT_DECODED", "Header: ")

        try {
            val split = JWTEncoded.split("\\.").toTypedArray()
            Log.d("JWT_DECODED", "Header: " + getJson(split[0]))
            Log.d("JWT_DECODED", "Body: " + getJson(split[1]))
        } catch (e: UnsupportedEncodingException) {
            //Error
        }
    }

    @Throws(UnsupportedEncodingException::class)
    private fun getJson(strEncoded: String): String {
        val decodedBytes: ByteArray = Base64.decode(strEncoded, Base64.URL_SAFE)
        return String(decodedBytes)
    }
}