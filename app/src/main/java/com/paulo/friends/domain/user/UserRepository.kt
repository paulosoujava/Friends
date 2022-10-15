package com.paulo.friends.domain.user

import com.paulo.friends.domain.excpetion.DuplicateAccountException
import com.paulo.friends.singUp.SignUpState

class UserRepository(
    private val userCatalog: InMemoryUserCatalog = InMemoryUserCatalog()
) {

    fun signUp(
        email: String,
        password: String,
        about: String
    ): SignUpState {
        return try {
            val user = userCatalog.createUser(email, password, about)
            SignUpState.SignUp(user)
        } catch (exc: DuplicateAccountException) {
            SignUpState.DuplicatedAccount
        }
    }
}