package com.hfad.mynews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hfad.mynews.databinding.FragmentSavedNewsBinding
import com.hfad.mynews.ui.NewsViewModel


class SavedNewsFragment : Fragment() {

    private val newsViewModel: NewsViewModel by viewModels()
    private var _binding: FragmentSavedNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedNewsBinding.inflate(inflater, container, false)

        return binding.root
    }


}