package com.example.getflix.ui.fragments


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.FragmentListsBinding

import com.example.getflix.hideKeyboard
import com.example.getflix.models.*
import com.example.getflix.service.requests.CreateListRequest
import com.example.getflix.ui.adapters.ListsAdapter
import com.example.getflix.ui.viewmodels.ListViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class ListsFragment : Fragment() {

    private lateinit var viewModel: ListViewModel

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentListsBinding>(
            inflater, R.layout.fragment_lists,
            container, false
        )

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.getCustomerLists()

        activity?.toolbar!!.toolbar_title.text = getString(R.string.lists)
        val recView = binding?.listsList as RecyclerView



        viewModel.listOfLists.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.lists.isEmpty()) {
                    binding.btnAddList.visibility = View.VISIBLE
                    binding.listImage.visibility = View.VISIBLE
                    binding.listText.visibility = View.VISIBLE
                    binding.listsList.visibility = View.GONE
                    binding.addFab.visibility = View.GONE
                } else {
                    binding.btnAddList.visibility = View.GONE
                    binding.listImage.visibility = View.GONE
                    binding.listText.visibility = View.GONE
                    binding.listsList.visibility = View.VISIBLE
                    binding.addFab.visibility = View.VISIBLE
                    val listAdapter = ListsAdapter(it.lists,this)
                    recView.adapter = listAdapter
                    recView.setHasFixedSize(true)
                    listAdapter.submitList(it.lists)
                }

            }
        })

        binding.btnAddList.setOnClickListener {
            if (binding.btnAddList.visibility == View.VISIBLE) {
                var dialog = AlertDialog.Builder(context, R.style.MaterialAlertDialog_color)
                var dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
                var edit = dialogView.findViewById<TextInputEditText>(R.id.name)
                dialog.setView(dialogView)
                dialog.setCancelable(true)
                dialog.setIcon(R.drawable.ic_pencil)
                dialog.setTitle("Add A List")
                dialog.setNegativeButton("Cancel") { dialogInterface: DialogInterface, i: Int -> }
                dialog.setPositiveButton("Create") { dialogInterface: DialogInterface, i: Int ->
                    println(edit.text.toString())
                    hideKeyboard(requireActivity())
                    viewModel.createList(CreateListRequest(edit.text.toString()))
                }
                dialog.show()
            }
        }

        binding.addFab.setOnClickListener {
            if (binding.addFab.visibility == View.VISIBLE) {
                var dialog = AlertDialog.Builder(context, R.style.MaterialAlertDialog_color)
                var dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
                var edit = dialogView.findViewById<TextInputEditText>(R.id.name)
                dialog.setView(dialogView)
                dialog.setCancelable(true)
                dialog.setIcon(R.drawable.ic_pencil)
                dialog.setTitle("Add A List")
                dialog.setNegativeButton("Cancel") { dialogInterface: DialogInterface, i: Int -> }
                dialog.setPositiveButton("Create") { dialogInterface: DialogInterface, i: Int ->
                    println(edit.text.toString())
                    hideKeyboard(requireActivity())
                    viewModel.createList(CreateListRequest(edit.text.toString()))
                }
                dialog.show()
            }
        }


        return binding.root
    }


}
