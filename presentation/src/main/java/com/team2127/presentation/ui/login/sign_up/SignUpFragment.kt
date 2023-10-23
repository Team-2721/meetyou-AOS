package com.team2127.presentation.ui.login.sign_up

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.team2127.presentation.databinding.FragmentSignUpBinding
import com.team2127.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(
    FragmentSignUpBinding::inflate
) {
    private val viewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
    }

    override fun initListener() {
        super.initListener()
    }

    override fun initCollector() {
        super.initCollector()
    }
}
