package com.paulo.friends.presentation.singUp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.paulo.friends.R


@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel,
    navController: NavHostController
) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error))
    val compositionCry by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cry))
    val coroutineScope = rememberCoroutineScope()
    val screenState by remember {
        mutableStateOf(SignUpScreenState(coroutineScope))
    }

    val state = signUpViewModel.signUpState.observeAsState()

    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        /*if (passwordError || emailError)
            LottieAnimation(
                composition = composition,
                iterations = 1,
                modifier = Modifier.size(120.dp)
            )*/
        Box {
            when (state.value) {
                SignUpState.BadEmail -> {
                    screenState.emailError = true
                    screenState.toggleInfoMessage(R.string.emailError)

                }
                SignUpState.BadPassword -> {
                    screenState.passwordError = true
                    screenState.toggleInfoMessage(R.string.passwordError)

                }
                SignUpState.DuplicatedAccount -> screenState.toggleInfoMessage(R.string.duplicateAccountError)
                SignUpState.BackendError -> screenState.toggleInfoMessage(R.string.backendError)
                SignUpState.Offline -> screenState.toggleInfoMessage(R.string.offlineError)
                SignUpState.Loading -> {

                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(80.dp)
                            .testTag(stringResource(id = R.string.loading)),
                        strokeWidth = 2.dp
                    )

                }
                else -> {}
            }

            Text(
                stringResource(R.string.account),
                color = Color.Black
            )

            if (screenState.currentInfoMessage != 0) {
                InfoMessage(
                    resource = screenState.currentInfoMessage,
                    isVisible = screenState.isInfoMessageShowing
                )
            }
        }
        EmailField(
            value = screenState.email,
            onValueChange = { screenState.email = it },
            isError = screenState.showBadEmail
        )
        PasswordField(
            value = screenState.password,
            onValueChange = { screenState.password = it },
            isError = screenState.showBadPassword
        )
        AboutField(
            value = screenState.about,
            onValueChange = { screenState.about = it }
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    screenState.resetUiState()
                    with(screenState) {
                        signUpViewModel.createAccount(
                            email, password, "",
                            navController
                        )
                    }

                }) {
                Text(stringResource(id = R.string.signUp))
            }
            /*if (passwordError || emailError)
                LottieAnimation(
                    composition = compositionCry,
                    iterations =  LottieConstants.IterateForever,
                    modifier = Modifier.size(90.dp)
                )*/
        }

    }

}

@Composable
fun InfoMessage(resource: Int, isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 150, easing = FastOutLinearInEasing)
        ),
        exit = fadeOut(
            targetAlpha = 0f,
            animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing)
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
                .padding(20.dp)
        ) {
            Text(
                stringResource(resource),
                color = Color.White
            )
        }

    }

}


@Composable
fun AboutField(
    onValueChange: (String) -> Unit,
    value: String
) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = value,
        label = {
            Text(stringResource(id = R.string.about))
        },
        onValueChange = onValueChange,
    )
}

@Composable
fun PasswordField(
    onValueChange: (String) -> Unit,
    value: String,
    isError: Boolean,
) {
    var isVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(stringResource(id = R.string.password)),
        value = value,
        label = {
            Text(stringResource(id = R.string.password))
        },
        onValueChange = onValueChange,
        singleLine = true,
        isError = isError,
        trailingIcon = {
            IconButton(onClick = {
                isVisible = !isVisible
            }) {
                Icon(Icons.Default.ThumbUp, contentDescription = null)
            }
        },
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun EmailField(
    onValueChange: (String) -> Unit,
    value: String,
    isError: Boolean,
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(stringResource(id = R.string.email)),

        value = value,
        isError = isError,
        label = {
            Text(stringResource(id = R.string.email))
        },
        singleLine = true,
        onValueChange = onValueChange
    )
}