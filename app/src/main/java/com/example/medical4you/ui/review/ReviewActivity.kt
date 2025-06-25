package com.example.medical4you.ui.review

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.medical4you.R

class ReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        val doctorId = intent.getIntExtra("doctor_id", -1)
        Log.d("ReviewActivity", "Doctor ID primit în intent: $doctorId")

        val fragment = ReviewFragment().apply {
            arguments = Bundle().apply {
                putInt("doctorId", doctorId)
            }
        }

        supportFragmentManager.commit {
            replace(R.id.review_nav_host, fragment)
        }
    }
}
