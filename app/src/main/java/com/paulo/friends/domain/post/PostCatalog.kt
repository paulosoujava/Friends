package com.paulo.friends.domain.post

import com.paulo.friends.domain.model.Post

interface PostCatalog {
    fun postsFor(userIds: List<String>): List<Post>
}