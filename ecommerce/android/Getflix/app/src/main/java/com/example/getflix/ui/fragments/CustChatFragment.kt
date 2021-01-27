package com.example.getflix.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.getflix.R
import com.example.getflix.databinding.FragmentCustChatBinding
import com.example.getflix.models.AuthorModel
import com.example.getflix.models.Message
import com.example.getflix.service.requests.SendMessageRequest
import com.example.getflix.ui.viewmodels.MessagesViewModel
import com.squareup.picasso.Picasso
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.messages.MessageInput
import com.stfalcon.chatkit.messages.MessagesListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
While writing this data class, I examined the sample code in the library, converted it to Kotlin
language and adapted it according to our app's needs.
Sample code can be found here: https://github.com/stfalcon-studio/ChatKit/blob/master/sample/src/main/java/com/stfalcon/chatkit/sample/features/demo/def/DefaultMessagesActivity.java ***/
class CustChatFragment : Fragment(), MessageInput.InputListener,
    MessageInput.AttachmentsListener  {

    private lateinit var messagesViewModel: MessagesViewModel
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

        name = args.name
        messagesViewModel = ViewModelProvider(this).get(MessagesViewModel::class.java)


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
            var rtime = message.date
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
            rtime = message.date.subSequence(0,index+1).toString() + hourS + message.date.subSequence(index+3,rtime.length).toString()
                messages.add(
                    Message(
                        "0", AuthorModel(
                            autid,
                            name,
                            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSVH3uxAhDbIZZqSLcgPoc3kpM1S0Vsy5VXg&usqp=CAU.jpg/format:webp",
                        ), message.text, Message.Image(null), LocalDateTime.parse(
                            rtime,
                            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
                        )
                    )
                )
            //}
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
            Message("1", AuthorModel("0", name, null), input.toString(),
                Message.Image(null), LocalDateTime.now()),
            true
        )
        messagesViewModel.sendMessage(SendMessageRequest(id.toInt(),input.toString(),null))
        return true
    }

    override fun onAddAttachments() {

    }






}