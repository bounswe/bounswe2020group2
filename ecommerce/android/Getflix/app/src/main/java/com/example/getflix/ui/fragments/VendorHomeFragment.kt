package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.activities.MainActivity
import com.example.getflix.databinding.FragmentVendorHomeBinding
import com.example.getflix.databinding.FragmentVendorProfileBinding
import com.example.getflix.models.BrandModel
import com.example.getflix.service.requests.VendorProUpdateRequest
import com.example.getflix.ui.adapters.SubCategoryAdapter
import com.example.getflix.ui.adapters.VendorHomeBrandsAdapter
import com.example.getflix.ui.adapters.VendorHomeProductsAdapter
import com.example.getflix.ui.viewmodels.SubCategoryViewModel
import com.example.getflix.ui.viewmodels.VendorHomeViewModel
import com.example.getflix.ui.viewmodels.VendorOrdersViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_vendor_home.*


class VendorHomeFragment : Fragment() {

    private lateinit var binding: FragmentVendorHomeBinding
    private lateinit var viewModel: VendorHomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vendor_home,
                container, false)

        activity?.bottom_nav!!.visibility = View.VISIBLE
        activity?.toolbar_lay!!.visibility = View.VISIBLE
        activity?.toolbar!!.toolbar_title.visibility = View.VISIBLE
        activity?.toolbar!!.toolbar_title.text = getString(R.string.home)
        viewModel = ViewModelProvider(this).get(VendorHomeViewModel::class.java)


        viewModel.searchByVendor(MainActivity.StaticData.user!!.id)
       /* var updateRequest = VendorProUpdateRequest(27,"Samsung S20 Ultra 128GB",10999.0,
            50,"Ekran Boyutu: 6.2', Ekran Çözünürlüğü: 1440x3200 px, Arka Kamera: 12 MP, Üçlü Kamera, Ön Kamera: 10 MP, 4G, Dahili Hafıza: 128 GB",
            "Galaxy S serisi akıllı cep telefonlarıyla nefes kesici teknolojik yenilikleri sergileyen Samsung, sinematik kare/saniye oranlarında ve 8K çözünürlükte video kaydı yapan dünyanın ilk telefonu olan Galaxy S20 ile teknoloji dünyasında yeniden gündemi belirliyor. Tasarım hatlarını Galaxy Note 10 ailesinden alan ve birçok donanımsal geliştirmeyle kullanıcıların karşısına çıkan Samsung Galaxy S20 128 GB modelleri, çoklu arka kamera kurulumu ve güçlü donanımıyla beğeni topluyor. Android 10 işletim sistemi ve Samsung one UI 2.0 kullanıcı arayüzüyle gelen ürün, çift SIM kart desteği sunuyor. Samsung Galaxy S20 128 GB fiyatları bakımından, yeni nesil amiral gemisi sınıfında yer alan telefonlarla benzerlik gösteriyor.",
            0.045,18,2) */
       // viewModel.updateVendorProduct(updateRequest)

        val recView = binding?.productList as RecyclerView
        val brandsView = binding?.brandList as RecyclerView

        val manager = GridLayoutManager(activity, 2)
        recView.layoutManager = manager

        val mLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL ,false)
        brandsView.layoutManager = mLayoutManager



        var brands = arrayListOf<BrandModel>()
        viewModel.productList.observe(viewLifecycleOwner, Observer{
            for(product in it) {
                if(!brands.contains(product.brand))
                    brands.add(product.brand)
            }
            MainActivity.StaticData.vendor = it[0].vendor.name
            MainActivity.StaticData.brandNum  = brands.size
            MainActivity.StaticData.proNum = it.size
            val productListAdapter = VendorHomeProductsAdapter(it!!,this)
            recView.adapter = productListAdapter
            recView.setHasFixedSize(true)
            val brandListAdapter = VendorHomeBrandsAdapter(brands)
            binding.brandList.adapter = brandListAdapter
            binding.brandList.setHasFixedSize(true)

        })

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        activity?.toolbar!!.btn_notification.visibility = View.GONE
    }


}