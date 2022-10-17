package com.paulo.friends.app

import com.paulo.friends.domain.user.InMemoryUserCatalog
import com.paulo.friends.domain.user.UserRepository
import com.paulo.friends.domain.validation.RegexCredentialsValidator
import com.paulo.friends.presentation.singUp.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module {

    single {  InMemoryUserCatalog()}

    factory { RegexCredentialsValidator() }
    factory { UserRepository( userCatalog = get()) }

    viewModel {
        SignUpViewModel(
            credentialsValidator = get(),
            userRepository = get()
        )
    }

}