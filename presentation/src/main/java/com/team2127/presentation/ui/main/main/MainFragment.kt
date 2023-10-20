package com.team2127.presentation.ui.main.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.team2127.presentation.databinding.FragmentMainBinding
import com.team2127.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: BaseFragment<FragmentMainBinding>(
    FragmentMainBinding::inflate
) {
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
    }
    
}
