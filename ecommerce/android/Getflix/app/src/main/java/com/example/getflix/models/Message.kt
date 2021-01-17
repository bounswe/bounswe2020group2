package com.example.getflix.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.MessageContentType
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

/**
   While writing this data class, I examined the sample code in the library, converted it to Kotlin
   language and adapted it according to our app's needs.
   Sample code can be found here: https://github.com/stfalcon-studio/ChatKit/blob/master/sample/src/main/java/com/stfalcon/chatkit/sample/common/data/model/Message.java ***/
data class Message(
    private val id: String,
    private var user: AuthorModel,
    private var text: String,
    private var image: Image,
    private var createdAt: LocalDateTime
) :
    IMessage, MessageContentType.Image,
    MessageContentType  {


    override fun getId(): String {
        return id
    }

    override fun getText(): String {
        return text
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getCreatedAt(): Date {
        val localDateTime = createdAt
        val zonedDateTime = localDateTime.atZone(ZoneOffset.systemDefault())
        val instant = zonedDateTime.toInstant()
        return Date.from(instant);
    }

    override fun getImageUrl(): String? {
       /* if(image.url==null || image.url=="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRG8VkAXFHGYAhHTEy4wAV5RBdB1V6qTU9JVA&usqp=CAU.jpg/format:webp") {
            return null
        } */
        return null
    }

    override fun getUser(): AuthorModel {
        return user
    }


    data class Image(val url: String?)


}