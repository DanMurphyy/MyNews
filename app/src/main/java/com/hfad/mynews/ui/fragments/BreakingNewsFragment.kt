package com.hfad.mynews.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.mynews.adapters.NewsAdapter
import com.hfad.mynews.databinding.FragmentBreakingNewsBinding
import com.hfad.mynews.ui.NewsViewModel
import com.hfad.mynews.utils.Resource


class BreakingNewsFragment : Fragment() {

    private val newsViewModel: NewsViewModel by viewModels()
    private val newsAdapter: NewsAdapter by lazy { NewsAdapter() }

    private var _binding: FragmentBreakingNewsBinding? = null
    private val binding get() = _binding!!
    private val TAG = "BreakingNewsFragment"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        setupRecyclerView()


        newsViewModel.breakingNews.observe(viewLifecycleOwner)
        { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data.articles.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occurred: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                else -> {
                    showProgressBar()
                }
            }
        }

        newsAdapter.setOnItemClickListener {
            val action =
                BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment(it)
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        binding.rvBreakingNews.adapter = newsAdapter
        binding.rvBreakingNews.layoutManager = LinearLayoutManager(requireContext())
    }

}