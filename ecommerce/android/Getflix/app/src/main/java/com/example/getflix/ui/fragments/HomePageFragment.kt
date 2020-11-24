package com.example.getflix.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.getflix.R
import com.example.getflix.databinding.FragmentHomePageBinding
import com.example.getflix.databinding.FragmentLoginBinding
import com.example.getflix.ui.fragment.CategoryFragment
import com.example.getflix.ui.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class HomePageFragment : Fragment() {

        private lateinit var homeViewModel: HomeViewModel

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            val binding = DataBindingUtil.inflate<FragmentHomePageBinding>(inflater,R.layout.fragment_home_page,
                    container,false)


            activity?.bottom_nav!!.visibility = View.VISIBLE

            activity?.toolbar_lay!!.visibility = View.VISIBLE
            activity?.toolbar!!.toolbar_title.text = getString(R.string.home)
            activity?.toolbar!!.btn_notification.visibility = View.VISIBLE

            homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
            binding.homeViewModel = homeViewModel
            binding.lifecycleOwner = this
            homeViewModel.onCategoryClick.observe(viewLifecycleOwner, Observer {
                println("buraya girdin")
                val transaction = activity?.supportFragmentManager!!.beginTransaction()
                val f = CategoryFragment()
                var bundle = Bundle()
                bundle.putInt("categoryId",it)
                f.arguments = bundle
                transaction.replace(R.id.my_nav_host_fragment, f)
                transaction.disallowAddToBackStack()
                transaction.commit()
                //  NavHostFragment.findNavController(this).navigate(HomePageFragmentDirections.actionHomePageFragmentToCategoryFragment(it!!))
            })

            return binding.root
        }

        override fun onStop() {
            super.onStop()
            activity?.toolbar!!.btn_notification.visibility = View.GONE
        }
    }






