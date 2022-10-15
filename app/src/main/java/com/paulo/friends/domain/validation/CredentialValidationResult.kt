package com.paulo.friends.domain.validation

sealed class CredentialValidationResult {
    object InvalidEmail : CredentialValidationResult()
    object InvalidPassword : CredentialValidationResult()
    object Valid : CredentialValidationResult()
}