package com.paulo.friends.singUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paulo.friends.domain.user.UserRepository
import com.paulo.friends.domain.validation.CredentialValidationResult
import com.paulo.friends.domain.validation.RegexCredentialsValidator


class SignUpViewModel(
    private val credentialsValidator: RegexCredentialsValidator,
    private val userRepository: UserRepository
) {

    private val _mutableSignUpState = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState> = _mutableSignUpState


    fun createAccount(
        email: String,
        password: String,
        about: String
    ) {
        when (credentialsValidator.validate(email, password)) {
            CredentialValidationResult.InvalidEmail -> _mutableSignUpState.value =
                SignUpState.BadEmail
            CredentialValidationResult.InvalidPassword -> _mutableSignUpState.value =
                SignUpState.BadPassword
            CredentialValidationResult.Valid -> {

                val result = userRepository.signUp(email, password, about)

                _mutableSignUpState.value = result
            }
        }
    }



}


