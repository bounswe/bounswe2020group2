package com.example.getflix.models

import com.stfalcon.chatkit.commons.models.IDialog


class Dialog(
    private val id: String, private val dialogName: String, private val dialogPhoto: String?,
    users: ArrayList<Author>, lastMessage: Message
) :
    IDialog<Message>  {
    private val users: ArrayList<Author> = users
    private var lastMessage: Message

    init {
        this.lastMessage = lastMessage
    }


    override fun getId(): String {
        return id
    }

    override fun getDialogPhoto(): String? {
        return dialogPhoto
    }

    override fun getDialogName(): String {
        return dialogName
    }

    override fun getUsers(): ArrayList<Author> {
        return users
    }

    override fun getLastMessage(): Message {
        return lastMessage
    }

    override fun setLastMessage(lastMessage: Message) {
        this.lastMessage = lastMessage
    }

    override fun getUnreadCount(): Int {
      return 0
    }


}