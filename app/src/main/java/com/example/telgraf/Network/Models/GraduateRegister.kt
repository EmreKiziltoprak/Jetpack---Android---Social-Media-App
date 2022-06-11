package com.example.telgraf.Network.Models
import com.google.gson.annotations.SerializedName

data class GraduateRegisterSuccess(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("identityNumber")
    val identityNumber: Any?,
    @SerializedName("name")
    val name: String,
    @SerializedName("schoolNumber")
    val schoolNumber: String,
    @SerializedName("surname")
    val surname: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("userId")
    val userId: Int
)

data class GraduateRegisterError(
    @SerializedName("className")
    val className: String,
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Any?,
    @SerializedName("errors")
    val errors: Any?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("name")
    val name: String
)

class Data

data class Error(
    @SerializedName("instance")
    val instance: İnstance,
    @SerializedName("message")
    val message: String,
    @SerializedName("origin")
    val origin: String,
    @SerializedName("path")
    val path: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("validatorArgs")
    val validatorArgs: List<Any>,
    @SerializedName("validatorKey")
    val validatorKey: String,
    @SerializedName("validatorName")
    val validatorName: Any?,
    @SerializedName("value")
    val value: String
)

data class İnstance(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("departmentId")
    val departmentId: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Any?,
    @SerializedName("isVerified")
    val isVerified: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("surname")
    val surname: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("verifyChanges")
    val verifyChanges: String,
    @SerializedName("verifyExpires")
    val verifyExpires: String,
    @SerializedName("verifyToken")
    val verifyToken: String
)