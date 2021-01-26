package com.example.getflix.ui.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.getflix.R
import com.example.getflix.databinding.FragmentVendorBinding
import com.example.getflix.doneAlert
import com.example.getflix.hideKeyboard
import com.example.getflix.service.requests.CreateListRequest
import com.example.getflix.service.requests.SendMessageRequest
import com.example.getflix.ui.adapters.VendorPageFragmentAdapter
import com.example.getflix.ui.adapters.VendorPageProductAdapter
import com.example.getflix.ui.viewmodels.MessagesViewModel
import com.example.getflix.ui.viewmodels.VendorPageViewModel
import com.example.getflix.vendorModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class VendorPageFragment : Fragment() {
    private lateinit var binding: FragmentVendorBinding
    private lateinit var vendorPageViewModel: VendorPageViewModel
    private lateinit var messagesViewModel: MessagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_vendor,
            container, false
        )



        val args = VendorPageFragmentArgs.fromBundle(requireArguments())
        val vendor = args.vendor
        vendorModel = vendor
        vendorPageViewModel = ViewModelProvider(this).get(VendorPageViewModel::class.java)
        binding.lifecycleOwner = this
        binding.vendorName.text = vendor.name
        messagesViewModel = ViewModelProvider(this).get(MessagesViewModel::class.java)

        binding.viewPager.adapter = VendorPageFragmentAdapter(requireActivity())
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.setIcon(R.drawable.ic_squares)
                1 -> tab.setIcon(R.drawable.ic_grid)
                else -> tab.setIcon(R.drawable.ic_speech_bubble)
            }
        }.attach()
        binding.vendorRating.text = vendor.rating.toString().subSequence(0,3)
        setVendorRating(vendor.rating)

        binding.btnMessage.setOnClickListener {
            var dialog = AlertDialog.Builder(context,R.style.MaterialAlertDialog_color)
            var dialogView = layoutInflater.inflate(R.layout.custom_messagedialog,null)
            var edit = dialogView.findViewById<TextInputEditText>(R.id.name)
            dialog.setView(dialogView)
            dialog.setCancelable(true)
            dialog.setIcon(R.drawable.ic_message)
            dialog.setTitle("Send A Message")
            dialog.setNegativeButton("Cancel") { dialogInterface: DialogInterface, i: Int -> }
            dialog.setPositiveButton("Send") { dialogInterface: DialogInterface, i: Int ->
                messagesViewModel.sendMessage(SendMessageRequest(vendor.id,edit.text.toString(),null))
                hideKeyboard(requireActivity())
                doneAlert(this,"Your message is sent succesfully!",null)
            }
            dialog.show()
        }

        return binding.root

    }



    fun setVendorRating(rating: Double) {
        if (rating >= 1) {
            binding.vendorStar1.setImageResource(R.drawable.ic_filled_star)
        }
        if (rating >= 2) {
            binding.vendorStar2.setImageResource(R.drawable.ic_filled_star)
        }
        if (rating >= 3) {
            binding.vendorStar3.setImageResource(R.drawable.ic_filled_star)
        }
        if (rating >= 4) {
            binding.vendorStar4.setImageResource(R.drawable.ic_filled_star)
        }
        if (rating.toInt() == 5) {
            binding.vendorStar5.setImageResource(R.drawable.ic_filled_star)
        }
    }

    override fun onStop() {
        super.onStop()
        activity?.toolbar_lay!!.visibility = View.VISIBLE
    }

}
