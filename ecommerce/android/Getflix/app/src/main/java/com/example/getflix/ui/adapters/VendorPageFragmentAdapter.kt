package com.example.getflix.ui.adapters


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.getflix.ui.fragments.NByThreeGridFragment
import com.example.getflix.ui.fragments.NByTwoGridFragment
import com.example.getflix.ui.fragments.VendorReviewsFragment

class VendorPageFragmentAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        println(position.toString() + "Ben şu an adapteeredayım")
        return when(position){
             0-> NByTwoGridFragment()
             1-> NByThreeGridFragment()
            else-> VendorReviewsFragment()
        }

    }

}

