package com.paulo.friends.timeline

import com.paulo.friends.domain.excpetion.ConnectionUnavailableException
import com.paulo.friends.domain.model.Post
import com.paulo.friends.domain.post.PostCatalog

 class OfflinePostCatalog : PostCatalog {
    override fun postsFor(userIds: List<String>): List<Post> {
        throw ConnectionUnavailableException()
    }

}
