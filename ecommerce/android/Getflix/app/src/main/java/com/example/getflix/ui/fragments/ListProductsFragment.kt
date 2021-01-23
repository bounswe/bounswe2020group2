package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.FragmentListProductsBinding
import com.example.getflix.ui.adapters.ListProductsAdapter
import com.example.getflix.ui.adapters.SwipeToDeleteAddress
import com.example.getflix.ui.adapters.SwipeToDeleteListProduct
import com.example.getflix.ui.viewmodels.ListProductViewModel
import com.example.getflix.ui.viewmodels.ListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class ListProductsFragment : Fragment() {

    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentListProductsBinding>(
            inflater, R.layout.fragment_list_products,
            container, false
        )
        activity?.toolbar!!.toolbar_title.text = getString(R.string.products)

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        val recView = binding?.listProductList as RecyclerView


        // takes arguments and prints products here
        val args = ListProductsFragmentArgs.fromBundle(requireArguments())
        val productList = args.products.toCollection(ArrayList())
        val listId = args.listId
        println(productList.toString())
        val listAdapter = ListProductsAdapter(productList)
        recView.adapter = listAdapter
        recView.setHasFixedSize(true)
        val itemTouchHelper =
            ItemTouchHelper(SwipeToDeleteListProduct(listAdapter))
        itemTouchHelper.attachToRecyclerView(recView)

        listAdapter.pos.observe(viewLifecycleOwner, Observer {
            if (it != -1) {
                println("---")
                println(it)
                println(listAdapter.itemCount)
                val id = productList[it].product.id
                println("AAAAA")
                println(id)
                println(listId)
                viewModel.deleteProductInList(listId,id)
                productList.removeAt(it)
                listAdapter!!.notifyDataSetChanged()
                listAdapter.resetPos()
            }
        })



        /*viewModel.listList.observe(viewLifecycleOwner, Observer {
            it?.let {
                listAdapter.submitList(it)
            }
        })  */


        return binding.root
    }

}