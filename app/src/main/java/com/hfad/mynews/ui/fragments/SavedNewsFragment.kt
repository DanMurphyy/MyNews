package com.hfad.mynews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.hfad.mynews.adapters.NewsAdapter
import com.hfad.mynews.databinding.FragmentSavedNewsBinding
import com.hfad.mynews.ui.NewsViewModel


class SavedNewsFragment : Fragment() {

    private val newsViewModel: NewsViewModel by viewModels()
    private var _binding: FragmentSavedNewsBinding? = null
    private val binding get() = _binding!!
    private val newsAdapter: NewsAdapter by lazy { NewsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val action =
                SavedNewsFragmentDirections.actionSavedNewsFragmentToArticleFragment(it)
            findNavController().navigate(action)
        }
        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                newsViewModel.deleteArticle(article)
                Snackbar.make(requireView(), "Successfully deleted article", Snackbar.LENGTH_SHORT)
                    .apply {
                        setAction("Undo") {
                            newsViewModel.saveArticle(article)
                        }
                    }
                    .show()
            }
        }

        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }

        newsViewModel.getAllArticles.observe(viewLifecycleOwner) { articles ->
            newsAdapter.differ.submitList(articles)
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.rvSavedNews.adapter = newsAdapter
        binding.rvSavedNews.layoutManager = LinearLayoutManager(requireContext())
    }


}