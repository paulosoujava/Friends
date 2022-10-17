package com.paulo.friends.sign

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.paulo.friends.MainActivity
import com.paulo.friends.domain.user.InMemoryUserCatalog
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class SignUpTest {

    @get:Rule
    val signUpTestRule = createAndroidComposeRule<MainActivity>()

    private val userCatalog = InMemoryUserCatalog()
    private val signUpModule = module{
        factory{ userCatalog}
    }

    @Before
    fun setUp(){
        loadKoinModules(signUpModule)
    }

    @Test
    fun performSignUp(){
        launchSignUpScreen(signUpTestRule){
            typeEmail("email@email.com")
            typePassword("Password123@! ")
            submit()
        } verify {
            timelineScreenIsPresent()
        }
    }

    @Test
    fun displayMessageErrorEmail(){
        launchSignUpScreen(signUpTestRule){
            val signedUpUserPassword = "@l1cePass"
            val signedUpUserEmail = "invalid_email"
            typeEmail(signedUpUserEmail)
            typePassword(signedUpUserPassword)
            submit()
        } verify {
            displayErrorEmailIsShown()
        }
    }

    @Test
    fun displayMessageErrorPassword(){
        launchSignUpScreen(signUpTestRule){
            val signedUpUserPassword = "invalid_password"
            val signedUpUserEmail = "valid@email.com"
            typeEmail(signedUpUserEmail)
            typePassword(signedUpUserPassword)
            submit()
        } verify {
            displayErrorPasswordIsShown()
        }
    }


    @Test
    fun displayDuplicateAccountError(){
        launchSignUpScreen(signUpTestRule){
            val signedUpUserPassword = "@l1cePass"
            val signedUpUserEmail = "alice@friends.com"

            createUserWith(signedUpUserEmail = signedUpUserEmail, signedUpUserPassword = signedUpUserPassword)

            typeEmail(signedUpUserEmail)
            typePassword(signedUpUserPassword)
            submit()

        } verify {
            duplicateAccountErrorIsShown()
        }
    }

    @After
    fun tearDown(){
        val resetModule = module{
            single{InMemoryUserCatalog()}
        }
        loadKoinModules(resetModule)
    }



    private fun createUserWith(
        signedUpUserEmail: String,
        signedUpUserPassword: String
    ){
        userCatalog.createUser(signedUpUserEmail, signedUpUserPassword, "")
    }
}