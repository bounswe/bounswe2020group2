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

import com.example.getflix.models.ReviewModel

class CommentAdapter :
    ListAdapter<ReviewModel, CommentAdapter.ViewHolder>(CommentDiffCallback()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private fun ViewHolder.bind(reviewModel: ReviewModel) {
        val comment = reviewModel.comment
        val profileImage = R.id.profile_image
        val commentDate = R.id.comment_date

        val constraintLayout = binding.constraintLayout
        binding.reviewer.text =
            reviewModel.reviewed_by.firstname + " " + reviewModel.reviewed_by.lastname
        if (comment.length < 180) {
            binding.shortComment.text = comment
        } else {
            binding.shortComment.text = comment.substring(0, 180) + "..."
        }
        binding.reviewRate.text =   reviewModel.rating.toString()
        binding.commentDate.text = reviewModel.review_date.substring(0,10) + " " + reviewModel.review_date.substring(11,19)
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
                constraintSet.connect(
                    commentDate,
                    ConstraintSet.BOTTOM,
                    R.id.long_comment,
                    ConstraintSet.TOP,
                    0
                )
                constraintSet.connect(
                    commentDate,
                    ConstraintSet.END,
                    R.id.long_comment,
                    ConstraintSet.END,
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
                constraintSet.connect(
                    commentDate,
                    ConstraintSet.BOTTOM,
                    R.id.short_comment,
                    ConstraintSet.TOP,
                    0
                )
                constraintSet.connect(
                    commentDate,
                    ConstraintSet.END,
                    R.id.short_comment,
                    ConstraintSet.END,
                    0
                )
                constraintSet.applyTo(constraintLayout)
                binding.shortComment.visibility = View.VISIBLE
                binding.longComment.visibility = View.GONE
            }
        }
        setCommentRating(reviewModel.rating)
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

        fun setCommentRating(rating: Int) {
            if (rating >= 1) {
                binding.star1.setImageResource(R.drawable.ic_filled_star)
            }
            if (rating >= 2) {
                binding.star2.setImageResource(R.drawable.ic_filled_star)
            }
            if (rating >= 3) {
                binding.star3.setImageResource(R.drawable.ic_filled_star)
            }
            if (rating >= 4) {
                binding.star4.setImageResource(R.drawable.ic_filled_star)
            }
            if (rating == 5) {
                binding.star5.setImageResource(R.drawable.ic_filled_star)
            }
        }

    }
}

class CommentDiffCallback : DiffUtil.ItemCallback<ReviewModel>() {
    override fun areItemsTheSame(oldItem: ReviewModel, newItem: ReviewModel): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: ReviewModel, newItem: ReviewModel): Boolean {
        return oldItem == newItem
    }

}

