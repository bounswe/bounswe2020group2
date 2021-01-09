package com.example.getflix.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.getflix.R
import com.example.getflix.databinding.FragmentCustMessagesBinding
import com.example.getflix.ui.fragments.CustMessagesFragmentDirections.Companion.actionCustMessagesFragmentToProfileFragment
import com.squareup.picasso.Picasso
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.commons.models.IDialog
import com.stfalcon.chatkit.dialogs.DialogsListAdapter
import com.stfalcon.chatkit.dialogs.DialogsListAdapter.OnDialogClickListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class CustMessagesFragment : Fragment() {


    private lateinit var binding: FragmentCustMessagesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_cust_messages,
            container, false
        )

        activity?.toolbar!!.toolbar_title.text = "Messages"

        val dialogsListAdapter: DialogsListAdapter<*> =
            DialogsListAdapter<IDialog<*>> { imageView, url, payload ->
                Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRG8VkAXFHGYAhHTEy4wAV5RBdB1V6qTU9JVA&usqp=CAU.jpg/format:webp").into(imageView)
            }

        binding.dialogsList.setAdapter(dialogsListAdapter)
        var dialogs = listOf(
            (Dialog(
                "1", "Samsung", null, arrayListOf<Author>(
                    Author("1", "Ali", null, true)
                ), Message("1", Author("2", "Ayse", null, false), "hello"), 2
            )), Dialog(
                "1", "Adidas", null, arrayListOf<Author>(
                    Author("1", "Ali", null, true)
                ), Message("1", Author("2", "Ayse", null, false), "hello"), 2
            )
        )
        dialogsListAdapter.setItems(dialogs as List<Nothing>?)

        dialogsListAdapter.setOnDialogClickListener {
            //On item click action
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


        return binding.root
    }


}