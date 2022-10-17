package com.idev.entreumart.ui.market.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.idev.entreumart.R
import com.idev.entreumart.data.remote.response.model.User
import com.idev.entreumart.databinding.FragmentProfileBinding
import com.idev.entreumart.ui.auth.login.LoginActivity
import com.idev.entreumart.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@Suppress("SENSELESS_COMPARISON")
@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()
    private var getFile: File? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        getToken()
        startGallery()
        logout()
    }

    private fun getToken() {
        viewModel.token.observe(viewLifecycleOwner) { token ->
            getUser(token)
            updateProfile(token)
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
                        binding.apply {
                            btnUpdate.isVisible = true
                            btnLogout.isVisible = true
                            layoutEmpty.root.isVisible = false
                        }
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    binding.apply {
                        btnUpdate.isVisible = false
                        btnLogout.isVisible = false
                        layoutEmpty.root.isVisible = true
                    }
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
                    .circleCrop()
                    .into(ivProfile)
            }
            edtUsername.setText(user.username)
            edtEmail.setText(user.email)
            edtPhoneNumber.setText(user.phoneNumber)
        }
    }

    private fun startGallery() {
        val intentGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                val selectedImg = it.data?.data as Uri
                val file = uriToFile(selectedImg, requireContext())
                getFile = file
                Glide.with(requireContext())
                    .load(selectedImg)
                    .circleCrop()
                    .into(binding.ivProfile)
            }
        }

        binding.ivProfile.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            intentGalleryLauncher.launch(intent)
        }
    }

    private fun updateProfile(token: String) {
        binding.apply {
            btnUpdate.setOnClickListener {
                var picture: File? = null
                if (getFile != null) {
                    picture = reduceFileImage(getFile as File)
                }

                val username = edtUsername.text.toString()
                val email = edtEmail.text.toString()
                val phoneNumber = edtPhoneNumber.text.toString()

                viewModel.updateUser(token, picture, username, email, phoneNumber)
                    .observe(viewLifecycleOwner) { result ->
                        when (result) {
                            is Resource.Loading -> {
                                showLoading(true)
                            }
                            is Resource.Success -> {
                                showLoading(false)
                                val data = result.data
                                if (data != null) {
                                    showMessage(data.message)
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
        if (state) showProgress(requireContext())
        else hideProgress()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}