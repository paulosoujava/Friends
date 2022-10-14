package com.paulo.friends.sign

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.paulo.friends.MainActivity
import org.junit.Rule
import org.junit.Test

class SignUpTest {

    @get:Rule
    val signUpTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun performSignUp(){
        launchSignUpScreen(signUpTestRule){
            typeEmail("email@email.com")
            typePassword("password")
            submit()
        } verify {
            timelineScreenIsPresent()
        }
    }
}