package com.paulo.friends.signUp

import com.paulo.friends.domain.excpetion.BackendException
import com.paulo.friends.domain.excpetion.ConnectionUnavailableException
import com.paulo.friends.domain.user.User
import com.paulo.friends.domain.user.UserCatalog
import com.paulo.friends.domain.user.UserRepository
import com.paulo.friends.presentation.singUp.SignUpState
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FailedAccountCreationTest {

    @Test
    fun backendError() = runBlocking{
        val userRepository = UserRepository(UnavailableUserCatalog())
        val result = userRepository.signUp(":email:", ":password:" ,":about:")
        assertEquals(SignUpState.BackendError, result)
    }

    @Test
    fun offLineError()= runBlocking{
        val userRepository = UserRepository(OfflineUseCatalog())
        val result = userRepository.signUp(":email:", ":password:" ,":about:")
        assertEquals(SignUpState.Offline, result)
    }
}

class OfflineUseCatalog : UserCatalog {
    override suspend fun createUser(email: String, password: String, about: String): User {
        throw ConnectionUnavailableException()
    }

    override fun followBy(userID: String): List<String> {
        TODO("Not yet implemented")
    }

}

class UnavailableUserCatalog : UserCatalog {
    override suspend fun createUser(email: String, password: String, about: String): User {
        throw BackendException()
    }

    override fun followBy(userID: String): List<String> {
        TODO("Not yet implemented")
    }

}
