package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import com.example.getflix.R
import com.example.getflix.databinding.FragmentAddressBinding
import com.example.getflix.models.AddressModel
import com.example.getflix.ui.adapters.AddressAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class AddressFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? {
        activity?.toolbar!!.toolbar_title.text = getString(R.string.addressInfo)
        val binding = DataBindingUtil.inflate<FragmentAddressBinding>(
                inflater, R.layout.fragment_address,
                container, false
        )

        var address1 =
            AddressModel(1, "Ev", "532983048", "Selin", "Zara", "Lawrence Moreno\n" +
                    "935-9940 Tortor. Street\n" +
                    "Santa Rosa MN 98804", "Santa Rosa", "citty", "country")
        var address2 =
            AddressModel(2, "Is", "532983048", "Selin", "Zara", "Lawrence Moreno\n" +
                    "935-9940 Tortor. Street\n" +
                    "Santa Rosa MN 98804", "Santa Rosa", "citty", "country")
        var address3 =
            AddressModel(3, "Yazlik", "532983048", "Selin", "Zara", "Lawrence Moreno\n" +
                    "935-9940 Tortor. Street\n" +
                    "Santa Rosa MN 98804", "Santa Rosa", "citty", "country")
        val addresses = arrayListOf(address1, address2, address3)

        val list = binding?.addressList as ListView
// 2
        val listItems = arrayOfNulls<AddressModel>(addresses.size)
// 3
        for (i in 0 until addresses.size) {
            val address = addresses[i]
            listItems[i] = address
        }
// 4
        val adapter = AddressAdapter(this, listItems)
        list.adapter = adapter


        return binding.root
    }


}