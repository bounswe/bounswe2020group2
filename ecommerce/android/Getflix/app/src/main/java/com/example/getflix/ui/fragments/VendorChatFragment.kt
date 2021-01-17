package com.example.getflix.ui.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.getflix.R
import com.example.getflix.activities.MainActivity
import com.example.getflix.databinding.FragmentVendorChatBinding
import com.example.getflix.models.Author
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


class VendorChatFragment : Fragment(), MessageInput.InputListener,
    MessageInput.AttachmentsListener {

    private lateinit var viewModel: MessagesViewModel
    private lateinit var binding: FragmentVendorChatBinding
    private lateinit var adapter: MessagesListAdapter<Message>
    private lateinit var autid: String
    private lateinit var id: String
    private lateinit var name: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_vendor_chat,
            container, false
        )
        viewModel = ViewModelProvider(this).get(MessagesViewModel::class.java)
        val args = VendorChatFragmentArgs.fromBundle(requireArguments())
        id = args.sender
        var messagesl = args.messages.toCollection(ArrayList())
        println(messagesl.toString())
        println("SENDER   " + id)
        name = args.name
        viewModel = ViewModelProvider(this).get(MessagesViewModel::class.java)


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
            /*if(message.text!=null && (message.attachmentUrl!=null || message.attachmentUrl!="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRG8VkAXFHGYAhHTEy4wAV5RBdB1V6qTU9JVA&usqp=CAU.jpg/format:webp")) {

               println("IFFFTEEEE")
                messages.add(Message("0", Author(
                    autid,
                    name,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSVH3uxAhDbIZZqSLcgPoc3kpM1S0Vsy5VXg&usqp=CAU.jpg/format:webp",
                ), message.text, Message.Image(null), LocalDateTime.parse(message.date,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")))
                )
                messages.add(Message("0", Author(
                    autid,
                    name,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSVH3uxAhDbIZZqSLcgPoc3kpM1S0Vsy5VXg&usqp=CAU.jpg/format:webp",
                ), "", Message.Image(message.attachmentUrl), LocalDateTime.parse(message.date,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")))
                )
            } else { */
            messages.add(
                Message(
                    "0", Author(
                        autid,
                        name,
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSVH3uxAhDbIZZqSLcgPoc3kpM1S0Vsy5VXg&usqp=CAU.jpg/format:webp",
                    ), message.text, Message.Image(null), LocalDateTime.parse(
                        message.date,
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
            Message("1", Author("0", name, null), input.toString(),
                Message.Image(null), LocalDateTime.now()),
            true
        )
        viewModel.sendMessage(SendMessageRequest(id.toInt(),input.toString(),"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRG8VkAXFHGYAhHTEy4wAV5RBdB1V6qTU9JVA&usqp=CAU.jpg/format:webp"))
        println(LocalDateTime.now().toString())
        return true
    }

    override fun onAddAttachments() {

    }


}