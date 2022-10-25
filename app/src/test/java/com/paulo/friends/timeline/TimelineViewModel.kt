package com.paulo.friends.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulo.friends.domain.user.UserCatalog
import com.paulo.friends.presentation.post.InMemoryPostCatalog
import com.paulo.friends.presentation.timeline.TimelineState

class TimelineViewModel(
    private val userCatalog: UserCatalog,
    private val memoryCatalog: InMemoryPostCatalog
): ViewModel() {


    private val _timelineState = MutableLiveData<TimelineState>()
    val timelineState: LiveData<TimelineState> = _timelineState


    fun timelineFor(userID: String) {
        val usersFollowBy = userCatalog.followBy(userID)
        val userIds = listOf(userID) + usersFollowBy

        _timelineState.value = TimelineState.Posts(memoryCatalog.postsFor(userIds))
    }

}
