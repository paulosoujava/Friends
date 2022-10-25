package com.paulo.friends.presentation.timeline

import com.paulo.friends.domain.model.Post

sealed class TimelineState {
    object BackendError : TimelineState()
    object OfflineError : TimelineState()
    data class Posts(val posts: List<Post>) : TimelineState()

}
