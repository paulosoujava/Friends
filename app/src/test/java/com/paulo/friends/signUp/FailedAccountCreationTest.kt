package com.paulo.friends.signUp

import com.paulo.friends.domain.excpetion.BackendException
import com.paulo.friends.domain.excpetion.ConnectionUnavailableException
import com.paulo.friends.domain.user.User
import com.paulo.friends.domain.user.UserCatalog
import com.paulo.friends.domain.user.UserRepository
import com.paulo.friends.presentation.singUp.SignUpState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FailedAccountCreationTest {

    @Test
    fun backendError(){
        val userRepository = UserRepository(UnavailableUserCatalog())
        val result = userRepository.signUp("email", "password" ,"about")
        assertEquals(SignUpState.BackendError, result)
    }

    @Test
    fun offLineError(){
        val userRepository = UserRepository(OfflineUseCatalog())
        val result = userRepository.signUp("email", "password" ,"about")
        assertEquals(SignUpState.Offline, result)
    }
}

class OfflineUseCatalog : UserCatalog {
    override fun createUser(email: String, password: String, about: String): User {
        throw ConnectionUnavailableException()
    }

}

class UnavailableUserCatalog : UserCatalog {
    override fun createUser(email: String, password: String, about: String): User {
        throw BackendException()
    }

}
