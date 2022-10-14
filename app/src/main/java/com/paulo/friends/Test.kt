package com.paulo.friends

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.tooling.preview.Preview

data class User(val name: String, val age: Int)


val localActiveUser = compositionLocalOf<User> { error("No user found!")  }


@Composable
fun TryCompositionLocal() {
    val user = User("Renata", 45)
    CompositionLocalProvider(localActiveUser provides user) {
        UserInf()
    }
}

@Composable
fun UserInf() {
    Column {
        Text(text = "Nome: " + localActiveUser.current.name)
        Text(text = "Nome: " + localActiveUser.current.age)
    }
}