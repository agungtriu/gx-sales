package com.agungtriu.gxsales.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.agungtriu.gxsales.R
import com.agungtriu.gxsales.base.BaseFragment
import com.agungtriu.gxsales.data.remote.request.LoginRequest
import com.agungtriu.gxsales.databinding.FragmentLoginBinding
import com.agungtriu.gxsales.utils.FormValidation
import com.agungtriu.gxsales.utils.UIState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObserver()
        setUpListener()
    }

    private fun setUpListener() {
        binding.btnLogin.setOnClickListener {
            if (!FormValidation.isEmailValid(binding.tietLoginEmail.text.toString())) {
                binding.tietLoginEmail.error = getString(R.string.login_invalid_email)
            } else if (!FormValidation.isPasswordValid(binding.tietLoginPassword.text.toString())) {
                binding.tietLoginPassword.error = getString(R.string.login_invalid_password)
            } else {
                viewModel.postLogin(
                    loginRequest = LoginRequest(
                        email = binding.tietLoginEmail.text.toString(),
                        password = binding.tietLoginPassword.text.toString()
                    )
                )
            }
        }
    }

    private fun setUpObserver() {
        viewModel.resultPostLogin.observe(viewLifecycleOwner) {
            when (it) {
                is UIState.Loading -> showLoading()
                is UIState.Error -> {
                    Snackbar.make(requireView(), it.error.message.toString(), Snackbar.LENGTH_LONG)
                        .show()
                    hideLoading()
                }

                is UIState.Success -> {
                    hideLoading()
                    findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                }
            }
        }
    }

    private fun showLoading() {
        binding.btnLogin.visibility = View.INVISIBLE
        binding.pbLogin.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.btnLogin.visibility = View.VISIBLE
        binding.pbLogin.visibility = View.INVISIBLE
    }

}