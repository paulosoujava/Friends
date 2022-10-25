package com.paulo.friends.signup

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.paulo.friends.MainActivity
import com.paulo.friends.R

fun launchSignUpScreen(
    rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    block: SignUpRobot.() -> Unit
): SignUpRobot {
    return SignUpRobot(rule).apply(block)
}

class SignUpRobot(
    private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {
    fun typeEmail(email: String) {
        val emailTag = rule.activity.getString(R.string.email)
        // rule.onNodeWithText(emailHint)
        rule.onNodeWithTag(emailTag)
            .performTextInput(email)
    }

    fun typePassword(password: String) {
        val passwordTag = rule.activity.getString(R.string.password)
        rule.onNodeWithTag(passwordTag)
            .performTextInput(password)
    }

    fun submit() {
        val signUp = rule.activity.getString(R.string.signUp)
        rule.onNodeWithText(signUp)
            .performClick()
    }

    infix fun verify(
        block: SignUpVerification.() -> Unit
    ): SignUpVerification {
        return SignUpVerification(rule).apply(block)
    }

}

class SignUpVerification(
    private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {
    fun timelineScreenIsPresent() {
        rule.onNodeWithText(rule.activity.getString(R.string.timeline))
            .assertIsDisplayed()
    }

    fun duplicateAccountErrorIsShown() {
        rule.onNodeWithText(rule.activity.getString(R.string.duplicateAccountError))
            .assertIsDisplayed()
    }

    fun displayErrorEmailIsShown() {
        rule.onNodeWithText(rule.activity.getString(R.string.emailError))
            .assertIsDisplayed()
    }

    fun badEmailErrorIsShown() {
        rule.onNodeWithText(rule.activity.getString(R.string.emailError))
            .assertDoesNotExist()
    }

    fun badPasswordErrorIsShown() {
        rule.onNodeWithText(rule.activity.getString(R.string.passwordError))
            .assertDoesNotExist()
    }

    fun displayErrorPasswordIsShown() {
        rule.onNodeWithText(rule.activity.getString(R.string.passwordError))
            .assertIsDisplayed()
    }

    fun backendErrorIsShown() {
        rule.onNodeWithText(rule.activity.getString(R.string.backendError))
            .assertIsDisplayed()
    }

    fun offlineErrorIsShown() {
        rule.onNodeWithText(rule.activity.getString(R.string.offlineError))
            .assertIsDisplayed()
    }

    fun blockingLoadingIsShown () {
        rule.onNodeWithTag(rule.activity.getString(R.string.loading))
            .assertIsDisplayed()
    }


}