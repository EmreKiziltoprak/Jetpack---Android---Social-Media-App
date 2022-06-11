package com.example.telgraf.Network.Models
import com.google.gson.annotations.SerializedName


data class LikePostBody(
    @SerializedName("postId")
    val postId: Int
)

data class LikePostError(
    @SerializedName("className")
    val className: String,
    @SerializedName("code")
    val code: Int,
    @SerializedName("errors")
    val errors: Errors2,
    @SerializedName("message")
    val message: String,
    @SerializedName("name")
    val name: String
)

class Errors2