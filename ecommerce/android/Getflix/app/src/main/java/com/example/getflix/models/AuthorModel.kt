package com.example.getflix.models

import com.stfalcon.chatkit.commons.models.IUser

/**
While writing this data class, I examined the sample code in the library, converted it to Kotlin
language and adapted it according to our app's needs.
Sample code can be found here: https://github.com/stfalcon-studio/ChatKit/blob/master/sample/src/main/java/com/stfalcon/chatkit/sample/common/data/model/User.java ***/
class AuthorModel(
    private val id: String,
    private val name: String,
    private val avatar: String?,
) :
    IUser {

    override fun getId(): String {
        return id
    }

    override fun getName(): String {
        return name
    }

    override fun getAvatar(): String? {
        return avatar
    }

}