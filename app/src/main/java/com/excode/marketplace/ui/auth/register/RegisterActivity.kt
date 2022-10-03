package com.excode.marketplace.ui.auth.register

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.excode.marketplace.data.remote.request.RegisterRequest
import com.excode.marketplace.databinding.ActivityRegisterBinding
import com.excode.marketplace.utils.Resource
import com.excode.marketplace.utils.dismissKeyboard
import com.excode.marketplace.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        register()
        navigateToLogin()
    }

    private fun register() {
        binding.apply {
            btnRegister.setOnClickListener {
                val username = edtUsername.text.toString()
                val phoneNumber = edtPhoneNumber.text.toString()
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                val confirmPassword = edtConfirmPassword.text.toString()

                val registerRequest =
                    RegisterRequest(username, email, phoneNumber, password, confirmPassword)
                it.dismissKeyboard()

                viewModel.register(registerRequest).observe(this@RegisterActivity) { result ->
                    when (result) {
                        is Resource.Loading -> {
                            showLoading(true)
                        }
                        is Resource.Success -> {
                            showLoading(false)
                            val data = result.data
                            if (data != null) {
                                showMessage(data.message)
                                finish()
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
        binding.progressBar.isVisible = state
        binding.btnRegister.isEnabled = !state
        window.decorView.clearFocus()
    }

    private fun showMessage(message: String?) {
        toast(message)
    }

    private fun navigateToLogin() {
        binding.tvLogin.setOnClickListener {
            finish()
        }
    }
}