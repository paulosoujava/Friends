package com.paulo.friends.singUp

import com.paulo.friends.domain.user.User

sealed class SignUpState {
    data class SignUp(val user: User) : SignUpState()
    object BadEmail : SignUpState()
    object BadPassword : SignUpState()
    object DuplicatedAccount : SignUpState()

}