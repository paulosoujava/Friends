package com.paulo.friends.domain.user

import com.paulo.friends.domain.excpetion.BackendException
import com.paulo.friends.domain.excpetion.ConnectionUnavailableException
import com.paulo.friends.domain.excpetion.DuplicateAccountException
import com.paulo.friends.presentation.singUp.SignUpState

class UserRepository(
    private val userCatalog: UserCatalog
) {

    suspend fun signUp(
        email: String,
        password: String,
        about: String
    ): SignUpState {
        return try {
            val user = userCatalog.createUser(email, password, about)
            SignUpState.SignUp(user)
        } catch (exc: DuplicateAccountException) {
            SignUpState.DuplicatedAccount
        }catch (backendException: BackendException){
            SignUpState.BackendError
        }catch (connectionException: ConnectionUnavailableException){
            SignUpState.Offline
        }
    }
}