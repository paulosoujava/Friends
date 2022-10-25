package com.paulo.friends.domain.model

data class Post(
    val postId: String,
    val userId: String,
    val postText: String,
    val timestamp: Long
)