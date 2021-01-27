package com.example.getflix.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.getflix.R
import com.example.getflix.databinding.FragmentCustMessagesBinding
import com.example.getflix.models.AuthorModel
import com.example.getflix.models.DialogModel
import com.example.getflix.models.Message
import com.example.getflix.models.MessageModel
import com.example.getflix.ui.fragments.CustMessagesFragmentDirections.Companion.actionCustMessagesFragmentToCustChatFragment
import com.example.getflix.ui.fragments.CustMessagesFragmentDirections.Companion.actionCustMessagesFragmentToProfileFragment
import com.example.getflix.ui.viewmodels.MessagesViewModel
import com.squareup.picasso.Picasso
import com.stfalcon.chatkit.commons.models.IDialog
import com.stfalcon.chatkit.dialogs.DialogsListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

/**
While writing this data class, I examined the sample code in the library, converted it to Kotlin
language and adapted it according to our app's needs.
Sample code can be found here: https://github.com/stfalcon-studio/ChatKit/blob/master/sample/src/main/java/com/stfalcon/chatkit/sample/features/demo/def/DefaultDialogsActivity.java ***/
class CustMessagesFragment : Fragment() {


    private lateinit var binding: FragmentCustMessagesBinding
    private lateinit var messagesViewModel: MessagesViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_cust_messages,
            container, false
        )

        activity?.toolbar!!.toolbar_title.text = "Messages"
        messagesViewModel = ViewModelProvider(this).get(MessagesViewModel::class.java)
        messagesViewModel.getMessages()


        messagesViewModel.messageList.observe(viewLifecycleOwner, Observer{
            var dialogList = arrayListOf<DialogModel>()
            var listOfMessageLists = arrayListOf<ArrayList<MessageModel>>()
            var i = 0
            for(conversation in it.conversations) {
                var rtime = conversation.messages[conversation.messages.size-1].date
                var index = rtime.indexOf("T")
                var hour = (rtime[index+1].toString()+ rtime[index+2].toString()).toInt()
                hour += 3
                if(hour==24)
                    hour=0
                else if(hour==25)
                    hour=1
                else if(hour==26)
                    hour=2
                var hourS = ""


                if(hour<10)
                    hourS = "0$hour"
                else
                    hourS = hour.toString()
                rtime = conversation.messages[conversation.messages.size-1].date.subSequence(0,index+1).toString() + hourS + conversation.messages[conversation.messages.size-1].date.subSequence(index+3,rtime.length).toString()
                dialogList.add(DialogModel(i.toString(),conversation.counterpart.name,null,Message(conversation.messages[conversation.messages.size-1].id.toString(),AuthorModel(conversation.counterpart.id.toString(), conversation.counterpart.name, null),conversation.messages[conversation.messages.size-1].text,
                    Message.Image(conversation.messages[conversation.messages.size - 1].attachmentUrl),
                    LocalDateTime.parse(rtime,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")))))
                i++;
                listOfMessageLists.add(conversation.messages as ArrayList<MessageModel>)
            }


            val dialogsListAdapter: DialogsListAdapter<*> =
                DialogsListAdapter<IDialog<*>> { imageView, url, payload ->
                    Picasso.get()
                        .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRG8VkAXFHGYAhHTEy4wAV5RBdB1V6qTU9JVA&usqp=CAU.jpg/format:webp")
                        .into(
                            imageView
                        )
                }

            binding.dialogsList.setAdapter(dialogsListAdapter)
            dialogsListAdapter.setItems(dialogList.toList() as List<Nothing>?)

            dialogsListAdapter.setOnDialogClickListener {
                view?.findNavController()!!
                    .navigate(actionCustMessagesFragmentToCustChatFragment(it.lastMessage.user.id,listOfMessageLists[it.id.toInt()].toTypedArray(),dialogList[it.id.toInt()].dialogName))
            }

            activity?.onBackPressedDispatcher!!.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if (isEnabled) {
                            isEnabled = false
                            view?.findNavController()!!
                                .navigate(actionCustMessagesFragmentToProfileFragment())
                        }
                    }
                }
            )
        })

        return binding.root
    }


}