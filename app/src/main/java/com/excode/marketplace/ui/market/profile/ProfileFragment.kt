package com.excode.marketplace.ui.market.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.excode.marketplace.R
import com.excode.marketplace.data.remote.response.User
import com.excode.marketplace.databinding.FragmentProfileBinding
import com.excode.marketplace.ui.auth.login.LoginActivity
import com.excode.marketplace.utils.Resource
import com.excode.marketplace.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@Suppress("SENSELESS_COMPARISON")
@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        getToken()
        updateProfile()
        logout()
    }

    private fun updateProfile() {
        binding.apply {
            btnUpdate.setOnClickListener {
                val username = edUsername.text.toString()
                val email = edtEmail.text.toString()
                val phoneNumber = edtPhoneNumber.text.toString()
            }
        }
    }

    private fun getToken() {
        viewModel.token.observe(viewLifecycleOwner) { token ->
            getUser(token)
        }
    }

    private fun getUser(token: String) {
        viewModel.getUser(token).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    val data = result.data
                    if (data != null) {
                        val user = data.data.user
                        showUser(user)
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    if (token.isNotEmpty()) {
                        showMessage(result.message)
                    }
                }
            }
        }
    }

    private fun showUser(user: User) {
        binding.apply {
            if (user.picture != null) {
                Glide.with(requireContext())
                    .load(user.picture)
                    .centerCrop()
                    .override(300)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivProfile)
            }
            edUsername.setText(user.username)
            edtEmail.setText(user.email)
            edtPhoneNumber.setText(user.phoneNumber)
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.isVisible = state
        requireActivity().window.decorView.clearFocus()
    }

    private fun showMessage(message: String?) {
        requireActivity().toast(message)
    }

    private fun logout() {
        binding.btnLogout.setOnClickListener {
            viewModel.clearLoginStatus()
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(requireContext(), LoginActivity::class.java))
        requireActivity().finish()
    }


}