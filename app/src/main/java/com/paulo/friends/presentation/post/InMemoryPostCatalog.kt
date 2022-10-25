package com.paulo.friends.presentation.post

import com.paulo.friends.domain.model.Post
import com.paulo.friends.domain.post.PostCatalog

class InMemoryPostCatalog(
    private val availablePosts: List<Post>
): PostCatalog {

   override fun postsFor(userIds: List<String>): List<Post> {
        return availablePosts.filter { userIds.contains(it.userId) }
    }
}