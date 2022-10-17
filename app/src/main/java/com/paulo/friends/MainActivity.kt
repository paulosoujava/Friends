package com.paulo.friends

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.paulo.friends.presentation.singUp.SignUpScreen
import com.paulo.friends.presentation.singUp.SignUpState
import com.paulo.friends.presentation.singUp.SignUpViewModel
import com.paulo.friends.presentation.timeline.Timeline
import com.paulo.friends.presentation.ui.theme.FriendsTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val signUpViewModel: SignUpViewModel by viewModel()


    private companion object {
        private const val SIGN_UP = "signUp"
        private const val TIMELINE = "timeline"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            FriendsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(navController = navController, startDestination = SIGN_UP) {
                        composable(SIGN_UP) {
                            SignUpScreen(
                                signUpViewModel = signUpViewModel,
                                onNavigate = {
                                    if (signUpViewModel.signUpState.value is SignUpState.SignUp)
                                        navController.navigate(TIMELINE)
                                })

                        }
                        composable(TIMELINE) {
                            Timeline()
                        }
                    }

                    // TryCompositionLocal()
                }
            }
        }
    }
}

