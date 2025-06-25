package com.example.medical4you.ui.review

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medical4you.R
import com.example.medical4you.data.model.Review

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    private var reviews: List<Review> = emptyList()

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val commentText: TextView = itemView.findViewById(R.id.tvComment)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBarItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.commentText.text = review.comment
        holder.ratingBar.rating = review.rating
    }

    override fun getItemCount(): Int = reviews.size

    fun submitList(newList: List<Review>) {
        reviews = newList
        notifyDataSetChanged()
    }
}
