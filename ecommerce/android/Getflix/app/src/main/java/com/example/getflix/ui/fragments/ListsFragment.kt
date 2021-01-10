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
import com.example.getflix.databinding.FragmentListsBinding
import com.example.getflix.models.*
import com.example.getflix.ui.adapters.ListsAdapter
import com.example.getflix.ui.viewmodels.ListViewModel
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
        binding.viewmodel = ListViewModel()
        activity?.toolbar!!.toolbar_title.text = getString(R.string.lists)
        val recView = binding?.listsList as RecyclerView
        /*var zaraJacket1 =
                ProductModel(10, "Jacket", "222", "1", "Zara", 1, 1, 1, "Nice jacket", "1", "1", "1", "1")
        var zaraJacket2 =
                ProductModel(11, "Jacket", "231", "1", "Zara", 1, 1, 1, "Cool jacket", "1", "1", "1", "1")
        var zaraJacket3 =
                ProductModel(12, "Jacket", "321", "1", "Zara", 1, 1, 1, "Amazing jacket", "1", "1", "1", "1")
        var zaraSkirt1 =
                ProductModel(4, "Skirt", "79", "1", "Zara", 1, 1, 1, "Nice skirt", "1", "1", "1", "1")
        var zaraSkirt2 =
                ProductModel(5, "Skirt", "93", "1", "Zara", 1, 1, 1, "Cool skirt", "1", "1", "1", "1")
        var zaraSkirt3 =
                ProductModel(6, "Skirt", "102", "1", "Zara", 1, 1, 1, "Amazing skirt", "1", "1", "1", "1")*/
        val list1 = listOf<String>()
        val list2 = mutableListOf<SubcategoryModel>()
        var product= ProductModel(27, "Samsung S20 Ultra", 10999.0, "2020-12-26T10:47:38.961041Z", 50, 11, 50,
        "Ekran Boyutu: 6.2', Ekran Çözünürlüğü: 1440x3200 px, Arka Kamera: 12 MP, Üçlü Kamera, Ön Kamera: 10 MP, 4G, Dahili Hafıza: 128 GB",
        SubcategoryModel("Cell Phones & Accessories", 2),
        "Galaxy S serisi akıllı cep telefonlarıyla nefes kesici teknolojik yenilikleri sergileyen Samsung, sinematik kare/saniye oranlarında ve 8K çözünürlükte video kaydı yapan",
            0.045 , CategoryModel("Electronics", 1, list2), BrandModel("Samsung",18), VendorModel(0.0,3, "Can Batuk"),
            4.545454545454546,
            list1, 10504.045, false)
        val products = arrayListOf(product)
        var list3 =
            ListModel(10, "My Summer Collection", products)
        var list4 =
            ListModel(20, "My Winter Collection", products)
        val lists = arrayListOf<ListModel>(list3, list4)
        val listAdapter = ListsAdapter(lists)
        recView.adapter = listAdapter
        recView.setHasFixedSize(true)

        for (list in lists) {
            viewModel.addList(list)
        }

        viewModel.listList.observe(viewLifecycleOwner, Observer {
            it?.let {
                listAdapter.submitList(it)
            }
        })

        return binding.root
    }


}
