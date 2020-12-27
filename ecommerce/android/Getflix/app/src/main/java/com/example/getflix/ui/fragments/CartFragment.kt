package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.FragmentCartBinding

import com.example.getflix.models.*

import com.example.getflix.models.CartProductModel

import com.example.getflix.ui.adapters.CartAdapter
import com.example.getflix.ui.fragments.CartFragmentDirections.Companion.actionCartFragmentToCompleteOrderFragment
import com.example.getflix.ui.viewmodels.CartViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class CartFragment : Fragment() {

    private lateinit var viewModel: CartViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentCartBinding>(inflater, R.layout.fragment_cart,
                container, false)

        activity?.toolbar!!.toolbar_title.text = getString(R.string.cart)
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        viewModel.getCustomerCartProducts()

        val recView = binding?.cartList as RecyclerView
        /*var zaraJacket1 =
                ProductModel(10, "Jacket", "222", "1", "Zara", 1, 1, 1, "Nice jacket", "1", "1", "1", "1")
        var zaraJacket2 =
                ProductModel(11, "Jacket", "231", "1", "Zara", 1, 1, 1, "Cool jacket", "1", "1", "1", "1")
        var zaraJacket3 =
                ProductModel(12, "Jacket", "32", "1", "Zara", 1, 1, 1, "cool", "Amazing jacket", "1", "1", "1")
        var zaraSkirt1 =
                ProductModel(4, "Skirt", "79", "1", "Zara", 1, 1, 1, "Nice skirt", "1", "1", "1", "1")
        var zaraSkirt2 =
                ProductModel(5, "Skirt", "93", "1", "Zara", 1, 1, 1, "Cool skirt", "1", "1", "1", "1")
        var zaraSkirt3 =
                ProductModel(6, "Skirt", "102", "1", "Zara", 1, 1, 1, "Amazing skirt", "1", "1", "1", "1")
        val products = arrayListOf(zaraSkirt3, zaraJacket1, zaraSkirt2, zaraJacket2, zaraSkirt1, zaraJacket3)*/

        /*var pro = ProductModel(id=3,
            "Skechers Men's Afterburn Memory-Foam Lace-up Sneaker", 50, "2020-08-11T08:22:34Z",
            50, 10, 22, "100% Leather/Synthetic, Heel measures approximately 2, Memory Foam Insole",
            SubcategoryModel("Sports & Outdoor", 5), "long one",0.1,
            CategoryModel("Health & Households", 2, mutableListOf<SubcategoryModel>(SubcategoryModel("women", 2),SubcategoryModel("men", 1))),
            BrandModel("Skechers" , 2), VendorModel(4.666666666666667, 1, "omerfaruk deniz"), 5,
            listOf("https://i.ebayimg.com/images/g/vOMAAOSwpeBeTK2g/s-l640.jpg", "https://www.shoes.com/pm/skech/skech681268_41824_jb1.jpg"),
            45)
        val products = arrayListOf<ProductModel>(pro)
        val productListAdapter = CartAdapter(products)
        recView.adapter = productListAdapter
        recView.setHasFixedSize(true) */


      /*  for (product in products) {
            viewModel.addProduct(product)
        } */

        binding.acceptOrder.setOnClickListener {
            view?.findNavController()?.navigate(actionCartFragmentToCompleteOrderFragment())
        }

        viewModel.cardProducts.observe(viewLifecycleOwner, {
            it?.let {
                val productListAdapter = CartAdapter(it!!)
                recView.adapter = productListAdapter
                recView.setHasFixedSize(true)
            }
        })


        /*viewModel.productList.observe(viewLifecycleOwner, Observer {
            it?.let {
                productListAdapter.submitList(it)
            }
        }) */

        return binding.root
    }


}
