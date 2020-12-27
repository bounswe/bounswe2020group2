package com.example.getflix.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getflix.R
import com.example.getflix.databinding.CardCommentBinding
import com.example.getflix.databinding.CardRecommendedProductBinding
import com.example.getflix.databinding.FragmentProductBinding
import com.example.getflix.models.ProductModel

class CommentAdapter :
    ListAdapter<String, CommentAdapter.ViewHolder>(CommentDiffCallback()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private fun ViewHolder.bind(comment: String) {
        val profileImage = R.id.profile_image
        val constraintLayout = binding.constraintLayout
        binding.reviewer.text = "Berk Turetken"
        if (comment.length < 180) {
            binding.shortComment.text = comment
            val params: ViewGroup.LayoutParams = constraintLayout.layoutParams
            params.height = 100
            constraintLayout.layoutParams = params
        } else {
            binding.shortComment.text = comment.substring(0, 180) + "..."
        }
        binding.root.setOnClickListener {
            if (comment.length > 180 && binding.shortComment.visibility == View.VISIBLE) {
                binding.longComment.text = comment
                val constraintSet: ConstraintSet = ConstraintSet()
                constraintSet.clone(constraintLayout)
                constraintSet.connect(
                    profileImage,
                    ConstraintSet.TOP,
                    R.id.long_comment,
                    ConstraintSet.TOP,
                    0
                )
                constraintSet.connect(
                    profileImage,
                    ConstraintSet.BOTTOM,
                    R.id.long_comment,
                    ConstraintSet.BOTTOM,
                    0
                )
                constraintSet.applyTo(constraintLayout)
                binding.shortComment.visibility = View.GONE
                binding.longComment.visibility = View.VISIBLE
            } else if (comment.length > 180 && binding.shortComment.visibility == View.GONE) {
                val constraintSet: ConstraintSet = ConstraintSet()
                constraintSet.clone(constraintLayout)
                constraintSet.connect(
                    profileImage,
                    ConstraintSet.TOP,
                    R.id.short_comment,
                    ConstraintSet.TOP,
                    0
                )
                constraintSet.connect(
                    profileImage,
                    ConstraintSet.BOTTOM,
                    R.id.short_comment,
                    ConstraintSet.BOTTOM,
                    0
                )
                constraintSet.applyTo(constraintLayout)
                binding.shortComment.visibility = View.VISIBLE
                binding.longComment.visibility = View.GONE
            }
        }
        setCommentRating(3.0.toDouble())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: CardCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CardCommentBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

        fun setCommentRating(commentRating: Double) {
            val rating = commentRating.toInt()
            if (rating >= 1) {
                binding.star1.setImageResource(R.drawable.ic_filled_star)
            }
            if (rating >= 2) {
                binding.star2.setImageResource(R.drawable.ic_filled_star)
            }
            if (rating >= 3) {
                binding.star2.setImageResource(R.drawable.ic_filled_star)
            }
            if (rating >= 4) {
                binding.star2.setImageResource(R.drawable.ic_filled_star)
            }
            if (rating == 5) {
                binding.star2.setImageResource(R.drawable.ic_filled_star)
            }
        }

    }
}

class CommentDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}

