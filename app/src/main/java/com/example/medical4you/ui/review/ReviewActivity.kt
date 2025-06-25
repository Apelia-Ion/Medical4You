package com.example.medical4you.ui.review

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.medical4you.R

class ReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        val doctorId = intent.getIntExtra("doctor_id", -1)
        Log.d("ReviewActivity", "Doctor ID primit în intent: $doctorId")

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.review_nav_host) as androidx.navigation.fragment.NavHostFragment

        navHostFragment.navController.setGraph(
            R.navigation.nav_graph,
            Bundle().apply {
                putInt("doctorId", doctorId)
            }
        )
    }
}
