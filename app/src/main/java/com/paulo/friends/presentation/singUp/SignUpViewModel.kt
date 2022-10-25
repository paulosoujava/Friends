package com.paulo.friends.presentation.singUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.paulo.friends.domain.user.UserRepository
import com.paulo.friends.domain.validation.CredentialValidationResult
import com.paulo.friends.domain.validation.RegexCredentialsValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SignUpViewModel(
    private val credentialsValidator: RegexCredentialsValidator,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _mutableSignUpState = MutableLiveData<SignUpState>(SignUpState.Initial)
    val signUpState: LiveData<SignUpState> = _mutableSignUpState


    fun createAccount(
        email: String,
        password: String,
        about: String,
        navController: NavController? = null
    ) {
        when (credentialsValidator.validate(email, password)) {
            CredentialValidationResult.InvalidEmail ->
                _mutableSignUpState.value = SignUpState.BadEmail
            CredentialValidationResult.InvalidPassword ->
                _mutableSignUpState.value = SignUpState.BadPassword
            CredentialValidationResult.Valid -> {

                _mutableSignUpState.value = SignUpState.Loading

                viewModelScope.launch {

                    _mutableSignUpState.value = withContext(Dispatchers.Unconfined) {
                        userRepository.signUp(email, password, about)
                    }

                    /*if (_mutableSignUpState.value is SignUpState.SignUp)
                        navController?.navigate(Constants.TIMELINE)*/
                }

            }
        }
    }
}


