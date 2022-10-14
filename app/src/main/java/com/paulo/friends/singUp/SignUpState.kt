package com.paulo.friends.singUp

sealed class SignUpState {
    object BadEmail: SignUpState()
}