package com.paulo.friends.signUp

import com.paulo.friends.InstantTaskExecutorExtension
import com.paulo.friends.domain.user.User
import com.paulo.friends.domain.validation.RegexCredentialsValidator
import com.paulo.friends.singUp.SignUpState
import com.paulo.friends.singUp.SignUpViewModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class CreateAnAccountTest {

    @Test
    fun accountCreated(){
        val viewModel = SignUpViewModel(RegexCredentialsValidator())
        val joe = User  ("joeId", "joe@email.com", "about Joe")
        viewModel.createAccount(joe.email, "Joe@2022", joe.about)

        assertEquals(SignUpState.SignUp(joe), viewModel.signUpState.value)
    }
    @Test
    fun anotherAccountCreated(){
        val viewModel = SignUpViewModel(RegexCredentialsValidator())
        val bob = User  ("bobId", "bob@email.com", "about Joe")
        viewModel.createAccount(bob.email, "Joe@2022", bob.about)

        assertEquals(SignUpState.SignUp(bob), viewModel.signUpState.value)
    }
    @Test
    fun createDuplicatedAccount(){
        val duplicatedAccount = User  ("anaId", "ana@email.com", "about Ana")

        val viewModel = SignUpViewModel(RegexCredentialsValidator()).also {
            it.createAccount(duplicatedAccount.email, "Joe@2022", duplicatedAccount.about)
        }

        viewModel.createAccount(duplicatedAccount.email, "Joe@2022", duplicatedAccount.about)
        assertEquals(SignUpState.DuplicatedAccount, viewModel.signUpState.value)
    }
}