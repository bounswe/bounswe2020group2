package com.example.getflix.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


class CustChatFragment : Fragment(), MessageInput.InputListener,
    MessageInput.AttachmentsListener  {


    private lateinit var adapter: MessagesListAdapter<Message>
    private lateinit var id: String
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

        binding.input.setInputListener(this)
        binding.input.setAttachmentsListener(this)

        activity?.bottom_nav!!.visibility = View.GONE

        var imageLoader: ImageLoader
        imageLoader = ImageLoader { imageView, url, payload ->
            Picasso.get().load(url).into(imageView)
        }

        activity?.toolbar!!.toolbar_title.text = id

        adapter = MessagesListAdapter<Message>("0", imageLoader)
        var messages = arrayListOf<Message>(
            Message(
                "1", Author(
                    "0",
                    "Samsung",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSVH3uxAhDbIZZqSLcgPoc3kpM1S0Vsy5VXg&usqp=CAU.jpg/format:webp",
                ), "0"
            ),
            Message(
                "2", Author(
                    "6",
                    "Samsung",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSVH3uxAhDbIZZqSLcgPoc3kpM1S0Vsy5VXg&usqp=CAU.jpg/format:webp",
                ), "6"
            ),
            Message(
                "3", Author(
                    "2",
                    "Samsung",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSVH3uxAhDbIZZqSLcgPoc3kpM1S0Vsy5VXg&usqp=CAU.jpg/format:webp",
                ), "2"
            ),
            Message(
                "4", Author(
                    "7",
                    "Samsung",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSVH3uxAhDbIZZqSLcgPoc3kpM1S0Vsy5VXg&usqp=CAU.jpg/format:webp",
                ), "7"
            )
        )
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


    override fun onSubmit(input: CharSequence) : Boolean {
        adapter.addToStart(
            Message("1", Author("0", "Samsung", null), input.toString()),
            true
        )
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