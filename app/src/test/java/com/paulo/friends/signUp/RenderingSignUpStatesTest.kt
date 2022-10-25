package com.paulo.friends.signUp

import com.paulo.friends.InstantTaskExecutorExtension
import com.paulo.friends.domain.user.InMemoryUserCatalog
import com.paulo.friends.domain.user.User
import com.paulo.friends.domain.user.UserRepository
import com.paulo.friends.domain.validation.RegexCredentialsValidator
import com.paulo.friends.presentation.singUp.SignUpState
import com.paulo.friends.presentation.singUp.SignUpViewModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class RenderingSignUpStatesTest {

    private val viewModel = SignUpViewModel(
        RegexCredentialsValidator(),
        UserRepository(InMemoryUserCatalog()),
    )
    private val tom = User("tomId", "tom@email.com", "about tom")


    @Test
    fun uiStatesAreDeliveredInParticularOrder() {

        val deliveredStates = mutableListOf<SignUpState>()

        viewModel.signUpState.observeForever{deliveredStates.add(it)}

        viewModel.createAccount(tom.email, "P@ssWord1#$", tom.about)


        assertEquals(
            listOf(
                SignUpState.Initial,
                SignUpState.Loading,
                SignUpState.SignUp(tom)
            ),
            deliveredStates
        )

    }


}