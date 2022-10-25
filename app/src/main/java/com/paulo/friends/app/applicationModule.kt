package com.paulo.friends.app

import com.paulo.friends.domain.post.PostCatalog
import com.paulo.friends.domain.timeline.TimelineRepository
import com.paulo.friends.domain.user.InMemoryUserCatalog
import com.paulo.friends.domain.user.UserCatalog
import com.paulo.friends.domain.user.UserRepository
import com.paulo.friends.domain.validation.RegexCredentialsValidator
import com.paulo.friends.presentation.post.InMemoryPostCatalog
import com.paulo.friends.presentation.singUp.SignUpViewModel
import com.paulo.friends.presentation.timeline.TimelineViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module {

    single<UserCatalog> { InMemoryUserCatalog() }
    single<PostCatalog> { InMemoryPostCatalog() }

    factory { RegexCredentialsValidator() }
    factory { UserRepository(userCatalog = get()) }

    factory {
        TimelineRepository(
            userCatalog = get(),
            postCatalog = get()
        )
    }

    viewModel {
        SignUpViewModel(
            credentialsValidator = get(),
            userRepository = get()
        )
    }

    viewModel {
        TimelineViewModel(
            timelineRepository = get()
        )
    }
}