package com.paulo.friends.domain.timeline

import com.paulo.friends.domain.excpetion.BackendException
import com.paulo.friends.domain.excpetion.ConnectionUnavailableException
import com.paulo.friends.domain.post.PostCatalog
import com.paulo.friends.domain.user.UserCatalog
import com.paulo.friends.presentation.timeline.TimelineState

class TimelineRepository(
    private val userCatalog: UserCatalog,
    private val postCatalog: PostCatalog
){
    fun getTimelineState(userID: String) = try {
        val usersFollowBy = userCatalog.followBy(userID)
        val userIds = listOf(userID) + usersFollowBy
        TimelineState.Posts(postCatalog.postsFor(userIds))
    } catch (e: BackendException) {
        TimelineState.BackendError
    } catch (e: ConnectionUnavailableException) {
        TimelineState.OfflineError
    }
}