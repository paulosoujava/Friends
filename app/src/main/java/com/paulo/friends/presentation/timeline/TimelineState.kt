package com.paulo.friends.presentation.timeline

import com.paulo.friends.domain.Post

sealed class TimelineState{
    data class Posts(val posts: List<Post>): TimelineState()

}
