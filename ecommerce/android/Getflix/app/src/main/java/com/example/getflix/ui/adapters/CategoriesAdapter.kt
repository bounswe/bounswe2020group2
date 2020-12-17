package com.example.getflix.ui.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.getflix.databinding.ListCategoryBinding
import com.example.getflix.databinding.ListSubcategoryBinding
import com.example.getflix.models.CategoryModel
import com.example.getflix.models.SubcategoryModel
import com.example.getflix.ui.fragments.CategoriesFragment
import com.example.getflix.ui.fragments.CategoriesFragmentDirections.Companion.actionCategoriesFragmentToSubcategoryFragment
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder

class CategoriesAdapter(groups: List<ExpandableGroup<*>>?, fragment: CategoriesFragment) :
        ExpandableRecyclerViewAdapter<CategoryViewHolder, SubCategoryViewHolder>(groups) {

    val fragment = fragment


        override fun onCreateGroupViewHolder(parent: ViewGroup?, viewType: Int): CategoryViewHolder {
            return CategoryViewHolder.from(parent!!)
        }

        override fun onCreateChildViewHolder(parent: ViewGroup?, viewType: Int): SubCategoryViewHolder {
            return SubCategoryViewHolder.from(parent!!)
        }

        override fun onBindChildViewHolder(
            holder: SubCategoryViewHolder?,
            flatPosition: Int,
            group: ExpandableGroup<*>?,
            childIndex: Int
        ) {
            val subCat: SubcategoryModel = group?.items?.get(childIndex) as SubcategoryModel
            holder?.bind(subCat)
            holder?.itemView!!.setOnClickListener {
                val subName = subCat.name
                fragment.findNavController().navigate(actionCategoriesFragmentToSubcategoryFragment(subName!!))
            }


        }



        override fun onBindGroupViewHolder(
            holder: CategoryViewHolder?,
            flatPosition: Int,
            group: ExpandableGroup<*>?
        ) {
            val continent: CategoryModel = group as CategoryModel
            holder?.bind(continent)
        }
    }

    class SubCategoryViewHolder(val binding: ListSubcategoryBinding) : ChildViewHolder(binding.root) {

        fun bind(subCategory: SubcategoryModel) {
            binding.subCategory = subCategory
            binding.root.setOnClickListener {v ->
              //  v.findNavController().navigate(actionCategoriesFragmentToSubcategoryFragment())
            }
        }




        companion object {
            fun from(parent: ViewGroup): SubCategoryViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListSubcategoryBinding.inflate(layoutInflater, parent, false)
                return SubCategoryViewHolder(binding)
            }
        }

    }

    class CategoryViewHolder(val binding: ListCategoryBinding) : GroupViewHolder(binding.root) {

        fun bind(category: CategoryModel) {
            binding.category = category
        }

        override fun expand() {
            super.expand()
            binding.arrow.animate().rotation(90F).duration = 0
        }

        override fun collapse() {
            super.collapse()
            binding.arrow.animate().rotation(0F).duration = 0
        }

        companion object {
            fun from(parent: ViewGroup): CategoryViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListCategoryBinding.inflate(layoutInflater, parent, false)
                return CategoryViewHolder(binding)
            }
        }

    }





