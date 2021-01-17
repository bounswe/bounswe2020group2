package com.example.getflix.ui.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.getflix.R
import com.example.getflix.categories
import com.example.getflix.databinding.FragmentNewHomeBinding
import com.example.getflix.hideKeyboard
import com.example.getflix.ui.adapters.*
import com.example.getflix.ui.fragments.HomePageFragment.StaticData.recyclerViewFirstPosition
import com.example.getflix.ui.fragments.HomePageFragmentDirections.Companion.actionHomePageFragmentToNotificationFragment
import com.example.getflix.ui.fragments.HomePageFragmentDirections.Companion.actionHomePageFragmentToSubcategoryFragment
import com.example.getflix.ui.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import me.relex.circleindicator.CircleIndicator2


class HomePageFragment : Fragment() {
    object StaticData {
        var recyclerViewFirstPosition = MutableLiveData<Int>()

    }

    private lateinit var homeViewModel: HomeViewModel

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentNewHomeBinding>(
            inflater, R.layout.fragment_new_home,
            container, false
        )


        activity?.bottom_nav!!.visibility = View.VISIBLE

        activity?.toolbar_lay!!.visibility = View.VISIBLE
        activity?.toolbar!!.toolbar_title.text = getString(R.string.home)
        activity?.toolbar!!.btn_notification.visibility = View.VISIBLE
        activity?.search!!.visibility = View.VISIBLE
        activity?.btn_search!!.visibility = View.VISIBLE
        activity?.toolbar!!.toolbar_title.visibility = View.GONE

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.lifecycleOwner = this

        activity?.search!!.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                activity?.toolbar!!.requestFocus()
            }
        }

        activity?.btn_search!!.setOnClickListener {
            println(activity?.search!!.text)
            var query = activity?.search!!.text.toString()
            activity?.search!!.text.clear()
            view?.findNavController()!!.navigate(actionHomePageFragmentToSubcategoryFragment(null,query,null,null,null,null))
        }

        activity?.btn_notification!!.setOnClickListener{
            view?.findNavController()!!.navigate(actionHomePageFragmentToNotificationFragment())
        }


        activity?.search!!.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                activity?.toolbar!!.requestFocus()
            }
        }

        activity?.btn_search!!.setOnClickListener {
            println(activity?.search!!.text)
            var query = activity?.search!!.text.toString()
            activity?.search!!.text.clear()
            view?.findNavController()!!.navigate(actionHomePageFragmentToSubcategoryFragment(null,query,null,null,null,null))
        }


        val adapterForHomeCategories = HomeCategoriesAdapter(viewLifecycleOwner)
        val adapterForTodaysDeals = TodaysDealsAdapter()
        val adapterForRecommendedProducts = HomeRecommenderAdapter()
        val adapterForTrendingProducts = TrendingProductAdapter()
        val adapterForEditorsPicks = HomeEditorsPicksAdapter()

        binding.categories.adapter = adapterForHomeCategories
        val layoutManagerForCategoriesAdapter =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.categories.layoutManager = layoutManagerForCategoriesAdapter
        adapterForHomeCategories.submitList(categories)


        recyclerViewFirstPosition.value = 0
        binding.categories.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val firstElementPosition =
                    layoutManagerForCategoriesAdapter.findFirstVisibleItemPosition()
                recyclerViewFirstPosition.value = firstElementPosition
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstElementPosition =
                    layoutManagerForCategoriesAdapter.findFirstVisibleItemPosition()
                recyclerViewFirstPosition.value = firstElementPosition


            }
        })


        binding.todaysDeals.adapter = adapterForTodaysDeals


        binding.trendingProducts.adapter = adapterForTrendingProducts

        val layoutManagerForTrendingProdutcts: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.trendingProducts.layoutManager = layoutManagerForTrendingProdutcts

        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding.todaysDeals)

        val indicator: CircleIndicator2 = binding.indicator
        indicator.attachToRecyclerView(binding.todaysDeals, pagerSnapHelper)
        indicator.createIndicators(4, 0);


        binding.homeRecommendedProducts.adapter = adapterForRecommendedProducts
        val layoutManagerForHomeRecommenderAdapter: RecyclerView.LayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.homeRecommendedProducts.layoutManager = layoutManagerForHomeRecommenderAdapter


        binding.editorPicks.adapter = adapterForEditorsPicks
        binding.editorPicks.layoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )

        val decoration = SpaceGenerator(18)
        binding.editorPicks.addItemDecoration(decoration)

        homeViewModel.todaysDeals?.observe(viewLifecycleOwner, Observer {
            adapterForTodaysDeals.submitList(it)
        })

        homeViewModel.recommendedProducts?.observe(viewLifecycleOwner, Observer {
            adapterForRecommendedProducts.submitList(it)
        })
        homeViewModel.editorPicks?.observe(viewLifecycleOwner, Observer {
            adapterForEditorsPicks.submitList(it)
        })
        homeViewModel.trendingProducts?.observe(viewLifecycleOwner, Observer {
            adapterForTrendingProducts.submitList(it)
        })

        HomeViewModel.onProductClick.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val productId = it.id
                HomeViewModel.onProductClick.value = null
                view?.findNavController()?.navigate(
                    HomePageFragmentDirections.actionHomePageFragmentToProductFragment2(
                        productId
                    )
                )

            }
        })
        return binding.root
    }

    override fun onStop() {
        super.onStop()
        activity?.toolbar!!.btn_notification.visibility = View.GONE
        activity?.search!!.visibility = View.GONE
        activity?.btn_search!!.visibility = View.GONE
        activity?.toolbar!!.toolbar_title.visibility = View.VISIBLE
    }
}