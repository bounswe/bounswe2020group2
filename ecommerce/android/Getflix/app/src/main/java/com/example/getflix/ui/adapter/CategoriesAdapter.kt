package com.example.getflix.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation.RELATIVE_TO_SELF
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.example.getflix.R
import com.example.getflix.model.CategoryModel
import com.example.getflix.model.SubcategoryModel
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder

    class CategoriesAdapter(groups: List<ExpandableGroup<*>>?) :
        ExpandableRecyclerViewAdapter<CategoryViewHolder, SubCategoryViewHolder>(
            groups
        ) {


        override fun onCreateGroupViewHolder(parent: ViewGroup?, viewType: Int): CategoryViewHolder {
            val itemView =
                LayoutInflater.from(parent?.context).inflate(R.layout.list_category, parent, false)
            return CategoryViewHolder(itemView)
        }

        override fun onCreateChildViewHolder(parent: ViewGroup?, viewType: Int): SubCategoryViewHolder {
            val itemView =
                LayoutInflater.from(parent?.context).inflate(R.layout.list_subcategory, parent, false)
            return SubCategoryViewHolder(itemView)
        }



        override fun onBindChildViewHolder(
            holder: SubCategoryViewHolder?,
            flatPosition: Int,
            group: ExpandableGroup<*>?,
            childIndex: Int
        ) {
            val subCat: SubcategoryModel = group?.items?.get(childIndex) as SubcategoryModel
            holder?.bind(subCat)
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

    class SubCategoryViewHolder(itemView: View) : ChildViewHolder(itemView) {
        val subCatName = itemView.findViewById<TextView>(R.id.subcat_name)

        fun bind(subCategory: SubcategoryModel) {
            subCatName.text = subCategory.name
        }
    }

    class CategoryViewHolder(itemView: View) : GroupViewHolder(itemView) {
        val catName = itemView.findViewById<TextView>(R.id.cat_name)
        val arrow = itemView.findViewById<ImageView>(R.id.arrow)
        //val arrow = itemView.findViewById<ImageView>(R.id.arrow)

        fun bind(category: CategoryModel) {
            catName.text = category.name
        }

        override fun expand() {
            super.expand()
            animateExpand()
        }

        override fun collapse() {
            super.collapse()
            animateCollapse()
        }

        private fun animateExpand() {
            var rotate = RotateAnimation(360F, 180F, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
            println("expanddesinn")
            rotate.duration = 300
            rotate.fillAfter = true
            arrow.animation = rotate
        }

        private fun animateCollapse() {
            var rotate = RotateAnimation(180F, 360F, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
            rotate.duration = 300
            rotate.fillAfter = true
            arrow.animation = rotate
        }
    }


