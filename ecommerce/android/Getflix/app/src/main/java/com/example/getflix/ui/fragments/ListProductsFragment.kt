package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.FragmentListProductsBinding
import com.example.getflix.ui.adapters.ListProductsAdapter
import com.example.getflix.ui.viewmodels.ListProductViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class ListProductsFragment : Fragment() {

    private lateinit var viewModel: ListProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentListProductsBinding>(
            inflater, R.layout.fragment_list_products,
            container, false
        )
        activity?.toolbar!!.toolbar_title.text = getString(R.string.products)

        viewModel = ViewModelProvider(this).get(ListProductViewModel::class.java)
        val recView = binding?.listProductList as RecyclerView


        // takes arguments and prints products here
        val args = ListProductsFragmentArgs.fromBundle(requireArguments())
        val productList = args.products.toCollection(ArrayList())
        println(productList.toString())
        val listAdapter = ListProductsAdapter(productList)
        recView.adapter = listAdapter
        recView.setHasFixedSize(true)



        /*viewModel.listList.observe(viewLifecycleOwner, Observer {
            it?.let {
                listAdapter.submitList(it)
            }
        })  */


        return binding.root
    }

}