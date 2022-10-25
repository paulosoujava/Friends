package com.paulo.friends.timeline

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.paulo.friends.MainActivity
import org.junit.Rule
import org.junit.Test





class TimelineScreenTest {

    @get:Rule
    val timelineTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun showsEmptyTimelineMessage() {
        val email = "paulo@gmail.com"
        val password = "123PAUpau*&*"
        launchTimelineFor(email, password, timelineTestRule) {

        } verify {
            emptyTimelineMessageIsDisplayed()
        }

    }


}


