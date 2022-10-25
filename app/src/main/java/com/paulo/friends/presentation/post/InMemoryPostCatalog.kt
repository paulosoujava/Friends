package com.paulo.friends.presentation.post

import com.paulo.friends.domain.Post

class InMemoryPostCatalog(
    private val availablePosts: List<Post>
) {

    fun postsFor(userIds: List<String>): List<Post> {
        return availablePosts.filter { userIds.contains(it.userId) }
    }
}