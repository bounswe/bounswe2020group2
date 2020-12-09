package com.example.getflix.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.ui.adapters.SubcategoryHorizontalAdapter
import com.example.getflix.databinding.FragmentCategoryBinding
import com.example.getflix.ui.viewmodels.CategoryViewModel
import kotlinx.android.synthetic.main.activity_main.*


class CategoryFragment : Fragment() {
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var adapter: SubcategoryHorizontalAdapter


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentCategoryBinding>(
                inflater, R.layout.fragment_category,
                container, false
        )

        activity?.toolbar!!.visibility = View.GONE
        val navController =
                Navigation.findNavController(requireActivity(), R.id.my_nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.toolbar.title = ""
        binding.toolbarTitle.text = "Category"

        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        val adapter = SubcategoryHorizontalAdapter(requireContext())
        categoryViewModel.displayedCategory.observe(viewLifecycleOwner, Observer {
            it.let {
                adapter.submitList(it.subCats)
            }
        })
        binding.lifecycleOwner = this
        binding.categoryList.adapter = adapter
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        binding.categoryList.layoutManager = layoutManager
        val args = CategoryFragmentArgs.fromBundle(requireArguments())
        var categoryId : Int = args.categoryId
        categoryViewModel.setCategory(categoryId)
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        activity?.toolbar!!.visibility = View.VISIBLE
    }
}
