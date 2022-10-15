package com.paulo.friends.domain.validation

import java.util.regex.Pattern

class RegexCredentialsValidator {

    private companion object {
        private const val EMAIL_REGEX =
            """[a-zA-Z0-9+._%\-]{1,64}@[a-zA-Z0-9][a-zA-Z0-9\-]{1,64}(\.[a-zA-Z0-9][a-zA-Z0-9\-]{1,25})"""

        private const val PASSWORD_REGEX =
            """^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*+=\-]).{8,}$"""
    }

    private val emailPattern = Pattern.compile(EMAIL_REGEX)
    private val passwordPattern = Pattern.compile(PASSWORD_REGEX)


    fun validate(
        email: String,
        password: String
    ): CredentialValidationResult {

        return if (!emailPattern.matcher(email).matches()) {
            CredentialValidationResult.InvalidEmail
        } else if (!passwordPattern.matcher(password).matches()) {
            CredentialValidationResult.InvalidPassword
        } else CredentialValidationResult.Valid

    }
}