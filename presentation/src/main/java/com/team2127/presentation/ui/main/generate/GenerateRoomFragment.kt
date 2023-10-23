package com.team2127.presentation.ui.main.generate

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.team2127.presentation.databinding.FragmentGenerateRoomBinding
import com.team2127.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenerateRoomFragment: BaseFragment<FragmentGenerateRoomBinding>(
    FragmentGenerateRoomBinding::inflate
) {
    private val viewModel: GeneateRoomViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
    }

    override fun initListener() {
    }

    override fun initCollector() {
    }
}
