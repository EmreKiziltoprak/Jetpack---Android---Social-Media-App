package com.example.telgraf.Network.Models
import com.google.gson.annotations.SerializedName


data class AnnouncementsModel(
    @SerializedName("data")
    val `data`: List<AnnouncementsModelData>,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("skip")
    val skip: Int,
    @SerializedName("total")
    val total: Int
)

data class AnnouncementsModelData(
    @SerializedName("content")
    val content: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("departmentId")
    val departmentId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("userInfo")
    val userInfo: UserInfo
)

data class UserInfo(
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("profile_picture")
    val profilePicture: String,
    @SerializedName("surname")
    val surname: String
)

data class JobAnnouncements(
    @SerializedName("data")
    val `data`: List<JobAnnouncementsData>,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("skip")
    val skip: Int,
    @SerializedName("total")
    val total: Int
)

data class JobAnnouncementsData(
    @SerializedName("content")
    val content: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("departmentId")
    val departmentId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("userInfo")
    val userInfo: UserInfo
)

