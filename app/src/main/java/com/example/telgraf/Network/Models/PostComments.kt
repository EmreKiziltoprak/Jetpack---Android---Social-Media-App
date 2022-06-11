package com.example.telgraf.Network.Models
import com.google.gson.annotations.SerializedName


data class PostComments(
    @SerializedName("data")
    val `data`: List<PostCommentsData>?,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("skip")
    val skip: Int,
    @SerializedName("total")
    val total: Int
)

data class PostCommentsData(
    @SerializedName("author")
    val author: Author,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("postId")
    val postId: Int,
    @SerializedName("text")
    val text: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)

data class SendCommentBody(
    @SerializedName("postId")
    val postId: Int,
    @SerializedName("text")
    val text: String
)