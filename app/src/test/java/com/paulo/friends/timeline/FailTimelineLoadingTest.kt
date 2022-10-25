package com.paulo.friends.timeline

import com.paulo.friends.InstantTaskExecutorExtension
import com.paulo.friends.domain.timeline.TimelineRepository
import com.paulo.friends.presentation.timeline.TimelineViewModel
import com.paulo.friends.domain.user.InMemoryUserCatalog
import com.paulo.friends.presentation.timeline.TimelineState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class FailTimelineLoadingTest {


    @Test
    fun backendError(){
        val viewModel = TimelineViewModel(
            TimelineRepository(InMemoryUserCatalog(),UnavailablePostCatalog())
        )
        viewModel.timelineFor(":irrelevant")
        assertEquals(
            TimelineState.BackendError, viewModel.timelineState.value
        )
    }
    @Test
    fun offLineError(){
        val viewModel = TimelineViewModel(
            TimelineRepository(InMemoryUserCatalog(),OfflinePostCatalog())
        )
        viewModel.timelineFor(":irrelevant")
        assertEquals(
            TimelineState.OfflineError, viewModel.timelineState.value
        )
    }
}