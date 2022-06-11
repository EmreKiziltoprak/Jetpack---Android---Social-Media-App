package com.example.telgraf.Network.Models
import com.google.gson.annotations.SerializedName


data class PostsModel(
    @SerializedName("data")
    val `data`: List<PostData>,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("skip")
    val skip: Int,
    @SerializedName("total")
    val total: Int
)

data class PostData(
    @SerializedName("author")
    val author: Author,
    @SerializedName("comment_count")
    val commentCount: Int,
    @SerializedName("content")
    val content: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("departmentId")
    val departmentId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isFileUploaded")
    val isFileUploaded: Boolean,
    @SerializedName("like_count")
    var likeCount: Int,
    @SerializedName("since")
    val since: Double,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("file")
    val file: FileX?,
    @SerializedName("user_like")
    val userLike: Boolean,
    @SerializedName("user_like_id")
    val userLikeİd: Int
)



data class FileX(
    @SerializedName("originalname")
    val originalname: String,
    @SerializedName("path")
    val path: String,
    @SerializedName("size")
    val size: Int
)

data class Author(
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

data class SinglePost(
    @SerializedName("author")
    val author: Author,
    @SerializedName("comment_count")
    val commentCount: Int,
    @SerializedName("content")
    val content: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("departmentId")
    val departmentId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isFileUploaded")
    val isFileUploaded: Boolean,
    @SerializedName("like_count")
    val likeCount: Int,
    @SerializedName("since")
    val since: Double,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("user_like")
    val userLike: Boolean,
    @SerializedName("user_like_id")
    val userLikeİd: Int
)

data class SendPostBody(
    @SerializedName("content")
    val content: String
)

data class AddImageError(
    @SerializedName("className")
    val className: String,
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("errors")
    val errors: Errors?,
    @SerializedName("message")
    val message: String,
    @SerializedName("name")
    val name: String
)
