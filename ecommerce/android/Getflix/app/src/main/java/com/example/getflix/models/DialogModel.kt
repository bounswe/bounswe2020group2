package com.example.getflix.models

import com.stfalcon.chatkit.commons.models.IDialog
import com.stfalcon.chatkit.commons.models.IUser

/**
While writing this data class, I examined the sample code in the library, converted it to Kotlin
language and adapted it according to our app's needs.
Sample code can be found here: https://github.com/stfalcon-studio/ChatKit/blob/master/sample/src/main/java/com/stfalcon/chatkit/sample/common/data/model/Dialog.java ***/
class DialogModel(
    private val id: String, private val dialogName: String, private val dialogPhoto: String?, lastMessage: Message
) :
    IDialog<Message>  {
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



    override fun getLastMessage(): Message {
        return lastMessage
    }

    override fun setLastMessage(lastMessage: Message) {
        this.lastMessage = lastMessage
    }

    override fun getUnreadCount(): Int {
      return 0
    }

    override fun getUsers(): MutableList<out IUser> {
        var list = mutableListOf<IUser>()
        return list
    }


}