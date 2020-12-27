package com.example.getflix.ui.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class SpaceGenerator(private val mSpace: Int) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = mSpace
        outRect.right = mSpace
        outRect.bottom = mSpace

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildAdapterPosition(view) == 0) outRect.top = mSpace
    }

}