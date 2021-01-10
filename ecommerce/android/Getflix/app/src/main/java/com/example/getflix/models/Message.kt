package com.example.getflix.models

import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.MessageContentType
import java.util.*



class Message(private val id: String, user: Author, private var text: String, createdAt: Date?) :
    IMessage, MessageContentType.Image,
    MessageContentType  {
    private var createdAt: Date
    private val user: Author
    private var image: Image? = null

    constructor(id: String, user: Author, text: String) : this(id, user, text, Date()) {}

    override fun getId(): String {
        return id
    }

    override fun getText(): String {
        return text
    }

    override fun getCreatedAt(): Date {
        return createdAt
    }

    override fun getUser(): Author {
        return user
    }

    override fun getImageUrl(): String? {
        return if (image == null) null else image!!.url
    }

    val status: String
        get() = "Sent"

    fun setText(text: String) {
        this.text = text
    }

    fun setCreatedAt(createdAt: Date) {
        this.createdAt = createdAt
    }

    fun setImage(image: Image?) {
        this.image = image
    }

    class Image(val url: String)

    init {
        this.user = user
        this.createdAt = createdAt!!
    }
}