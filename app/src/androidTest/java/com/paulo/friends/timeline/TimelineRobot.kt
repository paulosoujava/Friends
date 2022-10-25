package com.paulo.friends.timeline

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.paulo.friends.MainActivity
import com.paulo.friends.R
import com.paulo.friends.signup.launchSignUpScreen

typealias MainActivityRule = AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>


fun launchTimelineFor(
    email: String,
    password: String,
    timelineTestRule: MainActivityRule,
    block: TimelineRobot.() -> Unit
): TimelineRobot {

    launchSignUpScreen(timelineTestRule){
        typeEmail(email)
        typePassword(password)
        submit()
    }

    return TimelineRobot(timelineTestRule).apply(block)
}

class TimelineRobot(
    private val rule: MainActivityRule
) {
    infix fun verify(block: TimelineVerificationRobot.() -> Unit): TimelineVerificationRobot {
        return TimelineVerificationRobot(rule).apply(block)
    }
}

class TimelineVerificationRobot(
    private val rule: MainActivityRule
) {
  fun emptyTimelineMessageIsDisplayed(){
      val emptyTimelineMessage = rule.activity.getString(R.string.emptyTimleLine)
      rule.onNodeWithText(emptyTimelineMessage)
          .assertIsDisplayed()
  }

}
