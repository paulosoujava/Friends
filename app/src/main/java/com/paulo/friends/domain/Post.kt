package com.paulo.friends.domain

data class Post(
    val postId: String,
    val userId: String,
    val postText: String,
    val timestamp: Long
)