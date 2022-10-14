package com.paulo.friends.singUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SignUpViewModel {

    private val _mutableSignUpState = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState> = _mutableSignUpState

    fun createAccount(
        email: String,
        password: String,
        about: String
    ) {
        _mutableSignUpState.value = SignUpState.BadEmail
    }

}
