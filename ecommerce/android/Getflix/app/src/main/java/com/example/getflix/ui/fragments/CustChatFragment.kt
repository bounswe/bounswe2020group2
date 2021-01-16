package com.example.getflix.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.getflix.R
import com.example.getflix.databinding.FragmentCustChatBinding
import com.example.getflix.models.Author
import com.example.getflix.models.Message
import com.squareup.picasso.Picasso
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.messages.MessageInput
import com.stfalcon.chatkit.messages.MessagesListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class CustChatFragment : Fragment(), MessageInput.InputListener,
    MessageInput.AttachmentsListener  {


    private lateinit var adapter: MessagesListAdapter<Message>
    private lateinit var autid: String
    private lateinit var id: String
    private lateinit var name: String
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentCustChatBinding>(
            inflater, R.layout.fragment_cust_chat,
            container, false
        )
        val args = CustChatFragmentArgs.fromBundle(requireArguments())
        id = args.sender
        var messagesl = args.messages.toCollection(ArrayList())
        println(messagesl.toString())
        println("SENDER   " + id)
        name = args.name


        binding.input.setInputListener(this)
        binding.input.setAttachmentsListener(this)

        activity?.bottom_nav!!.visibility = View.GONE

        var imageLoader: ImageLoader
        imageLoader = ImageLoader { imageView, url, payload ->
            Picasso.get().load(url).into(imageView)
        }

        activity?.toolbar!!.toolbar_title.text = name
        var messages = arrayListOf<Message>()
        adapter = MessagesListAdapter<Message>("0", imageLoader)
        for(message in messagesl) {
            if(message.sentByMe) {
                autid = "0"
            }
            else {
                autid = "1"
            }
            messages.add(Message("0", Author(
                autid,
                name,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSVH3uxAhDbIZZqSLcgPoc3kpM1S0Vsy5VXg&usqp=CAU.jpg/format:webp",
            ), message.text, Message.Image(message.attachmentUrl), LocalDateTime.parse(message.date,
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")))
            )
        }

        // komple sağa ekliyor
        adapter.addToEnd(messages, true)


        // bir tane sağa ekliyor
        //adapter.addToStart(Message("1", Author("2", "Samsung", null, false), "xx"), true);

        binding.messagesList.setAdapter(adapter)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.bottom_nav!!.visibility = View.VISIBLE
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSubmit(input: CharSequence) : Boolean {
        adapter.addToStart(
            Message("1", Author("0", name, null), input.toString(), null, LocalDateTime.now()),
            true
        )
        println(LocalDateTime.now().toString())
        return true
    }

    override fun onAddAttachments() {

    }

    /*private fun getMessageStringFormatter(): MessagesListAdapter.Formatter<Message?>? {
        return MessagesListAdapter.Formatter { message ->
            val createdAt: String = SimpleDateFormat("MMM d, EEE 'at' h:mm a", Locale.getDefault())
                .format(message.createdAt)
            var text = message.text
            if (text == null) text = "[attachment]"
            java.lang.String.format(
                Locale.getDefault(), "%s: %s (%s)",
                message.user.name, text, createdAt
            )
        }
    } */




}