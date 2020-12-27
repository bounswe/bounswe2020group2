package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.databinding.CardCategoryBinding
import com.example.getflix.databinding.CardCategoryHighBinding
import com.example.getflix.getCategoryImage
import com.example.getflix.models.CategoryModel
import com.example.getflix.ui.fragments.HomePageFragment
import com.example.getflix.ui.fragments.HomePageFragmentDirections.Companion.actionHomePageFragmentToCategoryFragment

class HomeCategoriesAdapter(viewLifecycleOwner: LifecycleOwner) :
        ListAdapter<CategoryModel, HomeCategoriesAdapter.ViewHolder<CategoryModel>>(CategoryDiffCallback()) {

     var firstVisiblePosition = MutableLiveData<Int>()

    init{
        firstVisiblePosition.value = 0
        HomePageFragment.StaticData.recyclerViewFirstPosition.observe(viewLifecycleOwner,Observer {
            firstVisiblePosition.value = it
            notifyDataSetChanged()
    })
    }

    override fun onBindViewHolder(holder: ViewHolder<CategoryModel>, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<CategoryModel> {
        return when(viewType){
            0-> return ViewHolderHigh.from(parent)
            1-> return ViewHolderLow.from(parent)
            2-> return ViewHolderLow.from(parent)
            3-> return ViewHolderHigh.from(parent)
            else -> return ViewHolderLow.from(parent)
        }
    }

    override fun getItemViewType(position: Int) = position - firstVisiblePosition.value!!


     abstract class ViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
         abstract fun bind(item: T)
    }


    class ViewHolderLow private constructor(val binding: CardCategoryBinding) :
            ViewHolder<CategoryModel>(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolderLow {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CardCategoryBinding.inflate(layoutInflater, parent, false)
                return ViewHolderLow(binding)
            }
        }
         override fun bind(category: CategoryModel) {
             binding.root.setOnClickListener {
                 it.findNavController().navigate(actionHomePageFragmentToCategoryFragment(category.id))
             }
            binding.categoryImage.setImageResource(getCategoryImage(category.name))
            binding.categoryName.text = category.name
        }
    }
    class ViewHolderHigh private constructor(val binding: CardCategoryHighBinding) :
            ViewHolder<CategoryModel>(binding.root) {
        companion object {

            fun from(parent: ViewGroup): ViewHolderHigh {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CardCategoryHighBinding.inflate(layoutInflater, parent, false)
                return ViewHolderHigh(binding)
            }
        }
        override fun bind(category: CategoryModel) {
            binding.root.setOnClickListener {
                it.findNavController().navigate(actionHomePageFragmentToCategoryFragment(category.id))
            }
            binding.categoryImage.setImageResource(getCategoryImage(category.name))
            binding.categoryName.text = category.name
        }
    }


}

class CategoryDiffCallback : DiffUtil.ItemCallback<CategoryModel>() {
    override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
        return oldItem == newItem
    }

}
