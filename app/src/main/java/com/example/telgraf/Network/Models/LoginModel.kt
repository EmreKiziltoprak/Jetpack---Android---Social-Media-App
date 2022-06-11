package com.example.telgraf.Network.Models
import com.google.gson.annotations.SerializedName

data class AddPostBody(
    @SerializedName("content")
    val content: String
)

data class LoginBody(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("strategy")
    val strategy: String = "local"
)

data class LoginResponseModel(
    @SerializedName("accessToken")
    val accessToken: String?,
    @SerializedName("className")
    val className: String?,
    @SerializedName("code")
    val code: Int?,
    @SerializedName("errors")
    val errors: Errors?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("name")
    val name: String?
)

class Errors


 data class GraduateRegisterBody(
    @SerializedName("departmentId")
    val departmentId: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("schoolNumber")
    val schoolNumber: String,
    @SerializedName("surname")
    val surname: String
)

data class UserRegisterBody(
    @SerializedName("departmentId")
    val departmentId: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("surname")
    val surname: String
)