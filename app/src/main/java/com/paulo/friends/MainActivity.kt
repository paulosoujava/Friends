package com.paulo.friends

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.paulo.friends.presentation.singUp.SignUpScreen
import com.paulo.friends.presentation.singUp.SignUpViewModel
import com.paulo.friends.presentation.timeline.Timeline
import com.paulo.friends.presentation.ui.theme.FriendsTheme
import com.paulo.friends.util.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val signUpViewModel: SignUpViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state = signUpViewModel.signUpState.observeAsState()
            val navController = rememberNavController()

            FriendsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(navController = navController, startDestination = Constants.SIGN_UP) {
                        composable(Constants.SIGN_UP) {
                            SignUpScreen(
                                signUpViewModel = signUpViewModel,
                                navController = navController
                            )

                        }
                        composable(Constants.TIMELINE) {
                            Timeline()
                        }
                    }

                    // TryCompositionLocal()
                }
            }
        }
    }
}

