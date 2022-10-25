package com.paulo.friends.timeline

import com.paulo.friends.domain.excpetion.BackendException
import com.paulo.friends.domain.model.Post
import com.paulo.friends.domain.post.PostCatalog

 class UnavailablePostCatalog : PostCatalog {
    override fun postsFor(userIds: List<String>): List<Post> {
        throw BackendException()
    }

}
