package com.example.getflix.ui.fragments

import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.MessageContentType
import java.util.*


/*
 * Created by troy379 on 04.04.17.
 */
class Message(private val id: String, user: Author, private var text: String, createdAt: Date?) :
    IMessage, MessageContentType.Image,
    MessageContentType /*and this one is for custom content type (in this case - voice message)*/ {
    private var createdAt: Date
    private val user: Author
    private var image: Image? = null
    var voice: Voice? = null

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
    class Voice(val url: String, val duration: Int)

    init {
        this.user = user
        this.createdAt = createdAt!!
    }
}