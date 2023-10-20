package com.team2127.presentation.ui.splash

import android.content.Intent
import androidx.activity.viewModels
import com.team2127.presentation.MainActivity
import com.team2127.presentation.base.BaseActivity
import com.team2127.presentation.databinding.ActivitySplashBinding
import com.team2127.presentation.ui.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SplashActivity: BaseActivity<ActivitySplashBinding>(
    ActivitySplashBinding::inflate
) {

    private val viewModel: SplashViewModel by viewModels()

    override fun initCollector() {
        repeatOnStarted(this){
            viewModel.splashEvent.collectLatest { splach ->
                if (splach){
                    moveToLogin()
                }
            }
        }
    }

    private fun moveToLogin(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
