package com.example.getflix.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.categories
import com.example.getflix.databinding.FragmentNewHomeBinding
import com.example.getflix.ui.adapters.HomeCategoriesAdapter
import com.example.getflix.ui.adapters.HomeRecommenderAdapter
import com.example.getflix.ui.adapters.TodaysDealsAdapter
import com.example.getflix.ui.adapters.TrendingProductAdapter
import com.example.getflix.ui.fragments.HomePageFragment.StaticData.recyclerViewFirstPosition
import com.example.getflix.ui.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class HomePageFragment : Fragment() {
    object StaticData {
        var recyclerViewFirstPosition = MutableLiveData<Int>()

    }
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentNewHomeBinding>(inflater, R.layout.fragment_new_home,
                container, false)


        activity?.bottom_nav!!.visibility = View.VISIBLE

        activity?.toolbar_lay!!.visibility = View.VISIBLE
        activity?.toolbar!!.toolbar_title.text = getString(R.string.home)
        activity?.toolbar!!.btn_notification.visibility = View.VISIBLE

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.lifecycleOwner = this

        val adapterForHomeCategoriesAdapter = HomeCategoriesAdapter(viewLifecycleOwner)
        val adapterForTodaysDealsAdapter = TodaysDealsAdapter()
        val adapterForHomeRecommenderAdapter = HomeRecommenderAdapter()
        val adapterForTrendingProductAdapter = TrendingProductAdapter()

        binding.categories.adapter = adapterForHomeCategoriesAdapter
        val layoutManagerForCategoriesAdapter =  LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.categories.layoutManager = layoutManagerForCategoriesAdapter
        adapterForHomeCategoriesAdapter.submitList(categories)

        recyclerViewFirstPosition.value = 0
        binding.categories.addOnScrollListener( object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val firstElementPosition = layoutManagerForCategoriesAdapter.findFirstVisibleItemPosition()
                recyclerViewFirstPosition.value= firstElementPosition
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstElementPosition = layoutManagerForCategoriesAdapter.findFirstVisibleItemPosition()
                recyclerViewFirstPosition.value= firstElementPosition

            }
        })


        binding.todaysDeals.adapter = adapterForTodaysDealsAdapter
        val layoutManagerForTodaysDeals: RecyclerView.LayoutManager =  LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.todaysDeals.layoutManager = layoutManagerForTodaysDeals

        binding.trendingProducts.adapter = adapterForTrendingProductAdapter
        val layoutManagerForTrendingProducts: RecyclerView.LayoutManager =  LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.trendingProducts.layoutManager = layoutManagerForTrendingProducts


        binding.homeRecommendedProducts.adapter = adapterForHomeRecommenderAdapter
        val layoutManagerForHomeRecommenderAdapter: RecyclerView.LayoutManager =  LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.homeRecommendedProducts.layoutManager = layoutManagerForHomeRecommenderAdapter

       // binding.homeViewModel = homeViewModel
     /*   homeViewModel.onCategoryClick.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                view?.findNavController()?.navigate(actionHomePageFragmentToCategoryFragment(it!!))
                homeViewModel.navigationComplete()
            }
        })*/
        return binding.root
    }

    override fun onStop() {
        super.onStop()
        activity?.toolbar!!.btn_notification.visibility = View.GONE
    }
}