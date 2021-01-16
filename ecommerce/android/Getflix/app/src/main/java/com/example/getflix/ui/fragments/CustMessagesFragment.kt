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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.getflix.R
import com.example.getflix.databinding.FragmentCustMessagesBinding
import com.example.getflix.models.Author
import com.example.getflix.models.Dialog
import com.example.getflix.models.Message
import com.example.getflix.models.MessageModel
import com.example.getflix.ui.fragments.CustMessagesFragmentDirections.Companion.actionCustMessagesFragmentToCustChatFragment
import com.example.getflix.ui.fragments.CustMessagesFragmentDirections.Companion.actionCustMessagesFragmentToProfileFragment
import com.example.getflix.ui.viewmodels.CustMessagesViewModel
import com.squareup.picasso.Picasso
import com.stfalcon.chatkit.commons.models.IDialog
import com.stfalcon.chatkit.dialogs.DialogsListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class CustMessagesFragment : Fragment() {


    private lateinit var binding: FragmentCustMessagesBinding
    private lateinit var messagesViewModel: CustMessagesViewModel

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
        messagesViewModel = ViewModelProvider(this).get(CustMessagesViewModel::class.java)
        messagesViewModel.getMessages()

        messagesViewModel.messageList.observe(viewLifecycleOwner, {
            println("aaaa")
            println(it.toString())
            var dialogList = arrayListOf<Dialog>()
            var listOfMessageLists = arrayListOf<ArrayList<MessageModel>>()
            var i = 0
            for(conversation in it.conversations) {
                dialogList.add(Dialog(i.toString(),conversation.counterpart.name,null,Message(conversation.messages[conversation.messages.size-1].id.toString(),Author(conversation.counterpart.id.toString(), conversation.counterpart.name, null),conversation.messages[conversation.messages.size-1].text,
                    Message.Image(conversation.messages[conversation.messages.size - 1].attachmentUrl),
                    LocalDateTime.parse(conversation.messages[conversation.messages.size-1].date,
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
                println(it!!.dialogName)
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