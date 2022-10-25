package com.paulo.friends.signup

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.paulo.friends.MainActivity
import com.paulo.friends.domain.excpetion.BackendException
import com.paulo.friends.domain.excpetion.ConnectionUnavailableException
import com.paulo.friends.domain.user.InMemoryUserCatalog
import com.paulo.friends.domain.user.User
import com.paulo.friends.domain.user.UserCatalog
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class SignUpScreenTest {

    @get:Rule
    val signUpTestRule = createAndroidComposeRule<MainActivity>()


    private val signUpModule = module {
        factory<UserCatalog> { InMemoryUserCatalog() }
    }

    @Before
    fun setUp() {
        loadKoinModules(signUpModule)
    }

    @Test
    fun performSignUp() {
        launchSignUpScreen(signUpTestRule) {
            typeEmail("email@email.com")
            typePassword("Password123@!")
            submit()
        } verify {
            timelineScreenIsPresent()
        }
    }

    @Test
    fun displayMessageErrorEmail() {
        launchSignUpScreen(signUpTestRule) {
            typeEmail("invalid_email")
            typePassword("@l1cePass")
            submit()
        } verify {
            displayErrorEmailIsShown()
        }
    }

    @Test
    fun displayBadEmailError() {
        launchSignUpScreen(signUpTestRule) {
            typeEmail("email")
            submit()
        } verify {
            displayErrorEmailIsShown()
        }
    }

    @Test
    fun resetEmailError() {
        launchSignUpScreen(signUpTestRule) {
            typeEmail("email")
            submit()
            typeEmail("email@gmail.com")
            submit()
        } verify {
            badEmailErrorIsShown()
        }
    }

    @Test
    fun resetPasswordError() {
        launchSignUpScreen(signUpTestRule) {
            typeEmail("paulosouava@gmail.com")
            typePassword("ads")
            submit()
            typePassword("abcABC123@!")
            submit()
        } verify {
            badPasswordErrorIsShown()
        }
    }

    @Test
    fun displayMessageErrorPassword() {
        launchSignUpScreen(signUpTestRule) {
            typeEmail("valid@email.com")
            typePassword("invalid_password")
            submit()
        } verify {
            displayErrorPasswordIsShown()
        }
    }


    @Test
    fun displayDuplicateAccountError() = runBlocking<Unit>{
        val signedUpUserPassword = "@l1cePass"
        val signedUpUserEmail = "alice@friends.com"

        replace(InMemoryUserCatalog().apply {
            createUser(signedUpUserEmail, signedUpUserPassword, "")
        })

        launchSignUpScreen(signUpTestRule) {

            typeEmail(signedUpUserEmail)
            typePassword(signedUpUserPassword)
            submit()

        } verify {
            duplicateAccountErrorIsShown()
        }
    }


    @Test
    fun displayBackendError() {
        replace(UnavailableUserCatalog())

        launchSignUpScreen(signUpTestRule) {
            typeEmail("teste@test.com")
            typePassword("PpAa123!@#")
            submit()

        } verify {
            backendErrorIsShown()
        }
    }


    @Test
    fun displayOfflineError() {

        replace(OfflineUserCatalog())

        launchSignUpScreen(signUpTestRule) {
            typeEmail("teste@test.com")
            typePassword("PpAa123!@#")
            submit()

        } verify {
            offlineErrorIsShown()
        }
    }


    @Test
    fun displayBlockingLoading(){
        replace(DelayingUserCatalog())

        launchSignUpScreen(signUpTestRule) {
            typeEmail("teste@test.com")
            typePassword("PpAa123!@#")
            submit()

        } verify {
            blockingLoadingIsShown()
        }
    }


    @After
    fun tearDown() {
        replace(InMemoryUserCatalog())
    }


    private fun replace(userCatalog: UserCatalog) {
        val replaceModule = module {
            factory { userCatalog }
        }
        loadKoinModules(replaceModule)
    }


    class OfflineUserCatalog : UserCatalog {
        override  suspend fun createUser(email: String, password: String, about: String): User {
            throw ConnectionUnavailableException()
        }

        override fun followBy(userID: String): List<String> {
            TODO()
        }

    }

    class UnavailableUserCatalog : UserCatalog {
        override suspend fun createUser(email: String, password: String, about: String): User {
            throw BackendException()
        }
        override fun followBy(userID: String): List<String> {
            TODO()
        }

    }

    class DelayingUserCatalog  : UserCatalog {
        override suspend fun createUser(email: String, password: String, about: String): User {
            delay(1000)
            return User("idTest", email, about)
        }
        override fun followBy(userID: String): List<String> {
            TODO()
        }
    }

}



