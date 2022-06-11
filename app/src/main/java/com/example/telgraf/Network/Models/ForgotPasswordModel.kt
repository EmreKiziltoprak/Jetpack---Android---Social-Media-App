package com.example.telgraf.Network.Models
import com.google.gson.annotations.SerializedName


data class ForgotPBody(
    @SerializedName("action")
    var action: String = "sendResetPwd",

    @SerializedName("notifierOptions")
    var notifierOptions: NotifierOptions,

    @SerializedName("value")
    var value: Value
)

data class NotifierOptions(
    @SerializedName("preferredComm")
    var preferredComm: String = "email"
)

data class Value(
    @SerializedName("email")
    var email: String
)