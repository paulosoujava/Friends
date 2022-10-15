package com.paulo.friends.signUp


import com.paulo.friends.InstantTaskExecutorExtension
import com.paulo.friends.domain.validation.CredentialValidationResult
import com.paulo.friends.domain.validation.RegexCredentialsValidator
import com.paulo.friends.singUp.SignUpState
import com.paulo.friends.singUp.SignUpViewModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource


@ExtendWith(InstantTaskExecutorExtension::class)
class CredentialValidationTest {

    @ParameterizedTest
    @CsvSource(
        "'email'",
        "'a@b.c'",
        "'ab@b.c'",
        "'ab@bc.c'",
        "''",
        "'    '",
    )
    fun invalidEmail(email: String){
        val viewModel = SignUpViewModel(RegexCredentialsValidator())
        viewModel.createAccount(email, ":password:", ":about:")
        assertEquals(SignUpState.BadEmail, viewModel.signUpState.value   )
    }

    @ParameterizedTest
    @CsvSource(
        "''",
        "'   '",
        "'12345678'",
        "'abcd123'",
        "'abcDEF123'",
        "'abc124$#'",
        "'EF124$#'"
    )
    fun invalidPassword(password: String){
        val viewModel = SignUpViewModel(RegexCredentialsValidator())
        viewModel.createAccount("email@email.com", password, ":about:")
        assertEquals(SignUpState.BadPassword, viewModel.signUpState.value)
    }

    @Test
    fun validCredentials(){
        val validator =RegexCredentialsValidator()
        val result = validator.validate("email@email.com", "12Abcd3!")
        assertEquals(CredentialValidationResult.Valid, result)
    }
}