package com.paulo.friends.domain.user

import com.paulo.friends.domain.excpetion.DuplicateAccountException

class InMemoryUserCatalog(
    private val usersForPassword: MutableMap<String, MutableList<User>> = mutableMapOf()
): UserCatalog {
    override fun createUser(
        email: String,
        password: String,
        about: String
    ): User {
        if (usersForPassword.values.flatten().any {it.email == email}) {
            throw DuplicateAccountException()
        }
        val idUser = email.takeWhile { it != '@' } + "Id"
        val user = User(idUser, email, about)
        usersForPassword.getOrPut(password, ::mutableListOf).add(user)
        return user
    }
}