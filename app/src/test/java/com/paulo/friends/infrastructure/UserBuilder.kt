package com.paulo.friends.infrastructure

import com.paulo.friends.domain.user.User
import java.util.*

class UserBuilder{


    private var userId = UUID.randomUUID().toString()
    private var userEmail: String = "user@friends.com"
    private var userAbout: String = "I am a user"

    companion object{
        fun aUser(): UserBuilder{
            return UserBuilder()
        }
    }
    fun withId(id: String): UserBuilder  = this.apply{
        userId = id
    }
    fun build(): User {
        return User(userId,userEmail,userAbout)
    }
}