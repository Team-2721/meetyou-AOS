package com.team2127.presentation.ui.login.sign_up

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.team2127.presentation.databinding.FragmentSignUpBinding
import com.team2127.presentation.ui.base.BaseFragment
import com.team2127.presentation.ui.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

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
    }

    override fun initCollector() {
        repeatOnStarted(viewLifecycleOwner){
            viewModel.event.collectLatest {event ->
                handleEvent(event)
            }
        }
    }

    private fun handleEvent(event: SignUpViewModel.Event){
        when (event){
            SignUpViewModel.Event.SomethingEmpty -> {
                Toast.makeText(context, "닉네임, 아이디, 비밀번호, 비밀번호 확인을 모두 입력해 주세요", Toast.LENGTH_SHORT).show()
            }
            SignUpViewModel.Event.WrongLengthNickname -> {
                Toast.makeText(context, "닉네임은 2 ~ 20 글자 사이만 가능합니다.", Toast.LENGTH_SHORT).show()
            }
            SignUpViewModel.Event.HasConsonant -> {
                Toast.makeText(context, "닉네임에 자음만은 들어갈수 없어요", Toast.LENGTH_SHORT).show()
            }
            SignUpViewModel.Event.WrongLengthId -> {
                Toast.makeText(context, "아이디는 4 ~ 15 글자 사이만 가능합니다.", Toast.LENGTH_SHORT).show()
            }
            SignUpViewModel.Event.PleaseCheckId -> {
                Toast.makeText(context, "아이디는 영문 대소문자와 숫자만 가능합니다.", Toast.LENGTH_SHORT).show()
            }
            SignUpViewModel.Event.WrongLengthPassword -> {
                Toast.makeText(context, "비밀번호는 8 ~ 15 글자 사이만 가능합니다.", Toast.LENGTH_SHORT).show()
            }
            SignUpViewModel.Event.PleaseCheckPassword -> {
                Toast.makeText(context, "비밀번호는 영문 대소문자, 숫자, 특수문자를 포함해야 됩니다.", Toast.LENGTH_SHORT).show()
            }
            SignUpViewModel.Event.NotSamePassword -> {
                Toast.makeText(context, "비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }

            SignUpViewModel.Event.SuccessSignUp -> {
                Toast.makeText(context, "회원가입에 성공하셨습니다. 축하드립니다!!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
