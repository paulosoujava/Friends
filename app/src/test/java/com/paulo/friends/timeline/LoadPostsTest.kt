package com.paulo.friends.timeline

import com.paulo.friends.InstantTaskExecutorExtension
import com.paulo.friends.domain.Post
import com.paulo.friends.domain.user.Following
import com.paulo.friends.domain.user.InMemoryUserCatalog
import com.paulo.friends.infrastructure.UserBuilder
import com.paulo.friends.presentation.post.InMemoryPostCatalog
import com.paulo.friends.presentation.timeline.TimelineState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(InstantTaskExecutorExtension::class)
class LoadPostsTest {

    private val sara = UserBuilder.aUser().withId("saraId").build()
    private val tim = UserBuilder.aUser().withId("timId").build()
    private val anna = UserBuilder.aUser().withId("annaId").build()
    private val lucy = UserBuilder.aUser().withId("lucyId").build()

    private val timPosts = listOf(Post("postId3", tim.id, "post tim", 1L))
    private val lucyPosts = listOf(
        Post("postId1", lucy.id, "post lucy", 2L),
        Post("postId2", lucy.id, "post lucy", 2L),
    )
    private val saraPosts = listOf(
        Post("postId4", sara.id, "post sara", 1L),
    )

    private val availablePosts = timPosts + lucyPosts + saraPosts



    @Test
    fun noPostsAvailable() {
        val viewModel = TimelineViewModel(
            InMemoryUserCatalog(), InMemoryPostCatalog(availablePosts)
        )
        viewModel.timelineFor("tomId")

        assertEquals(
            TimelineState.Posts(emptyList()),
            viewModel.timelineState.value
        )
    }


    @Test
    fun postAvailable() {

        val viewModel =
            TimelineViewModel(InMemoryUserCatalog(), InMemoryPostCatalog(availablePosts))

        viewModel.timelineFor("timId")

        assertEquals(
            TimelineState.Posts(timPosts),
            viewModel.timelineState.value
        )
    }

    @Test
    fun postForFriends() {

        val viewModel = TimelineViewModel(
            InMemoryUserCatalog(
                followings = listOf(Following(anna.id, lucy.id))
            ), InMemoryPostCatalog(availablePosts)
        )

        viewModel.timelineFor(anna.id)

        assertEquals(
            TimelineState.Posts(lucyPosts),
            viewModel.timelineState.value
        )
    }

    @Test
    fun postsFromFriendsAlongOwn() {

        val viewModel = TimelineViewModel(
            InMemoryUserCatalog(
                followings = listOf(Following(sara.id, lucy.id))
            ), InMemoryPostCatalog(availablePosts)
        )

        viewModel.timelineFor(sara.id)

        assertEquals(
            TimelineState.Posts(lucyPosts + saraPosts),
            viewModel.timelineState.value
        )
    }
}