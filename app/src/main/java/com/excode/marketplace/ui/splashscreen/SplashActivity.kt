package com.excode.marketplace.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.excode.marketplace.databinding.ActivitySplashBinding
import com.excode.marketplace.ui.auth.login.LoginActivity
import com.excode.marketplace.ui.market.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getLoginStatus()
    }

    private fun getLoginStatus() {
        lifecycleScope.launch {
            delay(1000)

            viewModel.loginStatus.observe(this@SplashActivity) { isLogin ->
                if (isLogin) {
                    navigateToHome()
                } else {
                    navigateToLogin()
                }
            }
        }
    }

    private fun navigateToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}