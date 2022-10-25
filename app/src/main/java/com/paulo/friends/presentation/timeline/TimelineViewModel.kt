package com.paulo.friends.presentation.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulo.friends.domain.timeline.TimelineRepository

class TimelineViewModel(
    private val timelineRepository: TimelineRepository
) : ViewModel() {


    private val _timelineState = MutableLiveData<TimelineState>()
    val timelineState: LiveData<TimelineState> = _timelineState


    fun timelineFor(userID: String) {
        _timelineState.value = timelineRepository.getTimelineState(userID)
    }

}
