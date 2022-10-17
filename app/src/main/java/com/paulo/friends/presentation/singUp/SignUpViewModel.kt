package com.paulo.friends.presentation.singUp

import androidx.lifecycle.ViewModel
import com.paulo.friends.domain.user.UserRepository
import com.paulo.friends.domain.validation.CredentialValidationResult
import com.paulo.friends.domain.validation.RegexCredentialsValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class SignUpViewModel(
    private val credentialsValidator: RegexCredentialsValidator,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _mutableSignUpState = MutableStateFlow<SignUpState>(SignUpState.Initial)
    val signUpState: StateFlow<SignUpState> = _mutableSignUpState


    fun createAccount(
        email: String,
        password: String,
        about: String
    ) {
        when (credentialsValidator.validate(email, password)) {
            CredentialValidationResult.InvalidEmail ->
                _mutableSignUpState.value = SignUpState.BadEmail
            CredentialValidationResult.InvalidPassword ->
                _mutableSignUpState.value = SignUpState.BadPassword
            CredentialValidationResult.Valid -> {
                _mutableSignUpState.value = userRepository.signUp(email, password, about)
            }
        }
    }


}


