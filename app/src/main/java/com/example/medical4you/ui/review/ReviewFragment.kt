package com.example.medical4you.ui.review

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medical4you.R
import com.example.medical4you.data.MedicalAppDatabase
import com.example.medical4you.data.model.Review
import kotlinx.coroutines.launch

class ReviewFragment : Fragment() {

    private var doctorId: Int = -1
    private var patientId: Int = -1
    private lateinit var adapter: ReviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val db = MedicalAppDatabase.getDatabase(requireContext())
        val reviewDao = db.reviewDao()


        doctorId = arguments?.getInt("doctorId") ?: -1
        Log.d("ReviewDebug", "doctorId: $doctorId")


        val prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        patientId = prefs.getInt("user_id", -1)

        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        val editComment = view.findViewById<EditText>(R.id.editComment)
        val btnSubmit = view.findViewById<Button>(R.id.btnSubmit)
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerReviews)

        adapter = ReviewAdapter()
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        fun loadReviews() {
            lifecycleScope.launch {
                val reviews = reviewDao.getReviewsForDoctor(doctorId)
                Log.d("ReviewDebug", "Found ${reviews.size} reviews")
                adapter.submitList(reviews)
            }
        }

        loadReviews()

        btnSubmit.setOnClickListener {
            val rating = ratingBar.rating
            val comment = editComment.text.toString()

            if (rating == 0f || comment.isBlank()) {
                Toast.makeText(requireContext(), "Completează ratingul și comentariul", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val review = Review(
                doctorId = doctorId,
                patientId = patientId,
                rating = rating,
                comment = comment
            )

            lifecycleScope.launch {
                reviewDao.addReview(review)
                loadReviews()
                ratingBar.rating = 0f
                editComment.text.clear()
                Toast.makeText(requireContext(), "Recenzia a fost trimisă", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
