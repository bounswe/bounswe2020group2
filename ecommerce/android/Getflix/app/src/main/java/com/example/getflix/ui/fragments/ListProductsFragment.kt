package com.example.getflix.ui.fragments

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
import com.example.getflix.databinding.FragmentListProductsBinding
import com.example.getflix.databinding.FragmentListsBinding
import com.example.getflix.models.*
import com.example.getflix.ui.adapters.ListProductsAdapter
import com.example.getflix.ui.viewmodels.ListProductViewModel
import com.example.getflix.ui.viewmodels.ListViewModel
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

        val list1 = listOf<String>()
        val list2 = mutableListOf<SubcategoryModel>()
        var product= ProductModel(27, "Samsung S20 Ultra", 10999.0, "2020-12-26T10:47:38.961041Z", 50, 11, 50,
            "Ekran Boyutu: 6.2', Ekran Çözünürlüğü: 1440x3200 px, Arka Kamera: 12 MP, Üçlü Kamera, Ön Kamera: 10 MP, 4G, Dahili Hafıza: 128 GB",
            SubcategoryModel("Cell Phones & Accessories", 2),
            "Galaxy S serisi akıllı cep telefonlarıyla nefes kesici teknolojik yenilikleri sergileyen Samsung, sinematik kare/saniye oranlarında ve 8K çözünürlükte video kaydı yapan",
            0.045 , CategoryModel("Electronics", 1, list2), BrandModel("Samsung",18), VendorModel(0.0,3, "Can Batuk"),
            4.545454545454546,
            list1, 10504.045, false)
        var listproduct = ListProductModel(1, product)
        val listproducts = arrayListOf(listproduct)
        val listAdapter = ListProductsAdapter(listproducts)
        recView.adapter = listAdapter
        recView.setHasFixedSize(true)

        for (product in listproducts) {
            viewModel.addList(product)
        }

        viewModel.listList.observe(viewLifecycleOwner, Observer {
            it?.let {
                listAdapter.submitList(it)
            }
        })


        return binding.root
    }

}