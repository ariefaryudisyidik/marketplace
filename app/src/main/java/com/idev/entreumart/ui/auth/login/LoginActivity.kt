package com.idev.entreumart.ui.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.idev.entreumart.data.remote.request.LoginRequest
import com.idev.entreumart.databinding.ActivityLoginBinding
import com.idev.entreumart.ui.auth.register.RegisterActivity
import com.idev.entreumart.ui.main.MainActivity
import com.idev.entreumart.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        login()
        navigateToRegister()
    }

    private fun login() {
        binding.apply {
            btnLogin.setOnClickListener {
                val username = edtUsername.text.toString()
                val password = edtPassword.text.toString()

                val loginRequest = LoginRequest(username, password)
                it.dismissKeyboard()

                viewModel.login(loginRequest).observe(this@LoginActivity) { result ->
                    when (result) {
                        is Resource.Loading -> {
                            showLoading(true)
                        }
                        is Resource.Success -> {
                            showLoading(false)
                            val data = result.data
                            if (data != null) {
                                val token = data.data.token
                                viewModel.saveLoginStatus(token, true)
                                navigateToHome()
                            }
                        }
                        is Resource.Error -> {
                            showLoading(false)
                            showMessage(result.message)
                        }
                    }
                }

            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) showProgress(this)
        else hideProgress()
        window.decorView.clearFocus()
    }

    private fun showMessage(message: String?) {
        toast(message)
    }

    private fun navigateToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun navigateToRegister() {
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}