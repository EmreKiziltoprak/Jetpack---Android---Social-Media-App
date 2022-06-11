package com.example.telgraf.Network.Models
import com.google.gson.annotations.SerializedName


data class NotificationsModel(
    @SerializedName("data")
    val `data`: List<NotificationsModelData>?,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("skip")
    val skip: Int,
    @SerializedName("total")
    val total: Int
)

data class NotificationsModelData(
    @SerializedName("conversationId")
    val conversationId: Any?,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("notificationtype")
    val notificationtype: String,
    @SerializedName("other")
    val other: Other,
    @SerializedName("postId")
    val postId: Int,
    @SerializedName("recipientId")
    val recipientId: Int,
    @SerializedName("senderId")
    val senderId: Int,
    @SerializedName("updatedAt")
    val updatedAt: String
)

data class Other(
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("profile_picture")
    val profilePicture: Any?,
    @SerializedName("surname")
    val surname: String
)