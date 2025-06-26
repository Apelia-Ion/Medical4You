package com.example.medical4you.ui.pacient

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medical4you.R
import com.example.medical4you.data.model.NewsArticle
import com.example.medical4you.data.network.RetrofitInstance
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.RecyclerView

class NewsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.recyclerNews)
        adapter = NewsAdapter()

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        loadNews()
    }

    private fun loadNews() {
        lifecycleScope.launch {
            try {
                val articles: List<NewsArticle> = RetrofitInstance.api.getNews()
                adapter.submitList(articles)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Eroare la încărcarea știrilor", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
