package com.example.getflix.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.MessageContentType
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*


data class Message(
    private val id: String,
    private var user: Author,
    private var text: String,
    private var image: Image?,
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
        return image!!.url
    }

    override fun getUser(): Author {
        return user
    }


    data class Image(val url: String?)


}