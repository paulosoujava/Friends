package com.paulo.friends.singUp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paulo.friends.R


@Preview
@Composable
fun SignUp() {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(stringResource(R.string.account))
        EmailField(
            value = email,
            onValueChange = { email = it }
        )
        PasswordField(
            value = password,
            onValueChange = { password = it }
        )

        Button(onClick = { /*TODO*/ }) {
            Text(stringResource(id = R.string.signUp))
        }

    }

}

@Composable
fun PasswordField(
    onValueChange: (String) -> Unit,
    value: String
) {
    var isVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        label = {
            Text(stringResource(id = R.string.password))
        },
        onValueChange = onValueChange,
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
    value: String
) {
    OutlinedTextField(
        value = value,
        label = {
            Text(stringResource(id = R.string.email))
        },
        onValueChange = onValueChange
    )
}