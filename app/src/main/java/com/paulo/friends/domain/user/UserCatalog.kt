package com.paulo.friends.domain.user

interface  UserCatalog {
    suspend fun createUser(
        email: String,
        password: String,
        about: String
    ): User

    fun followBy(userID: String): List<String>
}