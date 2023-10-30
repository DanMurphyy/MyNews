package com.hfad.mynews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.hfad.mynews.databinding.FragmentArticleBinding
import com.hfad.mynews.ui.NewsViewModel

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel: NewsViewModel by viewModels()
    private val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        val article = args.article
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url!!)
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
        }

        binding.fab.setOnClickListener {
            newsViewModel.saveArticle(article)
            Snackbar.make(requireView(), "Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }

        return binding.root
    }

}