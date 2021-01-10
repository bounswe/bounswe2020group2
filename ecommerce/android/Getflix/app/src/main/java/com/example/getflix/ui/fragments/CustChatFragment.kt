package com.example.getflix.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.getflix.R
import com.example.getflix.databinding.FragmentCustChatBinding
import com.stfalcon.chatkit.messages.MessageInput
import com.stfalcon.chatkit.messages.MessagesListAdapter
import kotlinx.android.synthetic.main.activity_main.*


class CustChatFragment : Fragment(), MessageInput.InputListener,
    MessageInput.AttachmentsListener,
    MessageInput.TypingListener  {


    val adapter = MessagesListAdapter<Message>("1", null)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentCustChatBinding>(
            inflater, R.layout.fragment_cust_chat,
            container, false
        )
        val args = CustChatFragmentArgs.fromBundle(requireArguments())
        val id = args.sender

        activity?.bottom_nav!!.visibility = View.GONE




        val adapter = MessagesListAdapter<Message>(id, null)
        var messages = arrayListOf<Message>(
            Message("1",Author("2","Samsung",null,false),"heyyy"),
            Message("1",Author("2","Samsung",null,false),"heyyy")
        )
        // komple sağa ekliyor
        adapter.addToEnd(messages,false)

        // bir tane sağa ekliyor
        adapter.addToStart(Message("1",Author("2","Samsung",null,false),"xx"), true);

        binding.messagesList.setAdapter(adapter)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.bottom_nav!!.visibility = View.VISIBLE
    }


    override fun onSubmit(input: CharSequence) : Boolean {
        println("burdasınnnnnn")
        adapter.addToStart(Message("1",Author("2","Samsung",null,false),input.toString()), true)
        return true
    }

    override fun onAddAttachments() {

    }

    override fun onStartTyping() {
        println("start typing")
    }

    override fun onStopTyping() {

    }


}