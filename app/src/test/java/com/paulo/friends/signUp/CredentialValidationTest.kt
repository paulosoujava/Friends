package com.paulo.friends.signUp


import com.paulo.friends.InstantTaskExecutorExtension
import com.paulo.friends.singUp.SignUpState
import com.paulo.friends.singUp.SignUpViewModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(InstantTaskExecutorExtension::class)
class CredentialValidationTest {


    @Test
    fun invalidEmail(){
        val viewModel = SignUpViewModel()
        viewModel.createAccount("email", ":password:", ":about:")
        assertEquals(SignUpState.BadEmail, viewModel.signUpState.value   )
    }
}