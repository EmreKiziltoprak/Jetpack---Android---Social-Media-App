package com.example.telgraf.Network.Models
import com.google.gson.annotations.SerializedName


data class UserModel(
    @SerializedName("beginning")
    val beginning: String?,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("definition")
    val definition: String?,
    @SerializedName("ending")
    val ending: Any?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("institute")
    val institute: String,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("type")
    val type: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("userId")
    val userId: Int
)

data class UserMainModel(
    @SerializedName("bio")
    val bio: String?,

    @SerializedName("departmentId")
    val departmentId: Int?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("info")
    val info: List<İnfo>?,
    @SerializedName("isVerified")
    val isVerified: Boolean?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("profile_picture")
    val profilePicture: String?,
    @SerializedName("resetExpires")
    val resetExpires: Any?,
    @SerializedName("resetToken")
    val resetToken: Any?,
    @SerializedName("surname")
    val surname: String?,

    @SerializedName("verifyToken")
    val verifyToken: String
)

data class İnfo(
    @SerializedName("institute")
    val institute: String?,
    @SerializedName("phone")
    val phone: Any?,
    @SerializedName("type")
    val type: String?
)

data class EditUserInfoModel(

    @SerializedName("definition")
    val definition: String?=null,

    @SerializedName("institute")
    val institute: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("userId")
    val userId: Int
)

data class EditMainUserModel(
    @SerializedName("bio")
    val bio: String?,

    @SerializedName("name")
    val name: String?,
    @SerializedName("surname")
    val surname: String?
)