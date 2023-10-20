package com.team2127.presentation.ui.login.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.team2127.presentation.ui.base.BaseFragment
import com.team2127.presentation.databinding.FragmentLoginBinding
import com.team2127.presentation.ui.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
    }

    override fun initListener() {
        binding.btnLoginSignUp.setOnClickListener {
            moveToSignUp()
        }
    }

    override fun initCollector() {
        repeatOnStarted(viewLifecycleOwner){
            viewModel.event.collectLatest { event ->
                handleEvent(event)
            }
        }
    }

    private fun moveToMain(){}

    private fun moveToSignUp(){}

    private fun handleEvent(event: LoginViewModel.Event){
        when (event){
            LoginViewModel.Event.LoginFailed -> { Toast.makeText(context, "로그인에 실패 하였습니다. \n아이디와 비밀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show()}
            LoginViewModel.Event.LoginSuccess -> {
                Toast.makeText(context, "로그인에 성공 하였습니다.", Toast.LENGTH_SHORT).show()
                moveToMain()
            }
        }
    }
}
