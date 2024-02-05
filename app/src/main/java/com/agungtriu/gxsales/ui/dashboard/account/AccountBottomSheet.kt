package com.agungtriu.gxsales.ui.dashboard.account

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.agungtriu.gxsales.R
import com.agungtriu.gxsales.databinding.BottomSheetAccountBinding
import com.agungtriu.gxsales.utils.UIState
import com.agungtriu.gxsales.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetAccountBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val contextThemeWrapper = ContextThemeWrapper(requireActivity(), R.style.Theme_GXSales)
        _binding = BottomSheetAccountBinding.inflate(
            inflater.cloneInContext(contextThemeWrapper),
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val modalBottomSheetBehavior = (dialog as BottomSheetDialog).behavior
        modalBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        binding.tvBottomsheetaccountCopyright.text = Utils.copyright(requireContext())
        setUpObserver()
        setUpListener()
    }

    private fun setUpListener() {
        binding.btnBottomsheetaccountLogout.setOnClickListener {
            viewModel.postLogout()
        }
    }

    private fun setUpObserver() {
        viewModel.resultProfile.observe(viewLifecycleOwner) {
            when (it) {
                is UIState.Loading -> startShimmer()
                is UIState.Error -> stopShimmer()
                is UIState.Success -> {
                    stopShimmer()
                    binding.tvBottomsheetaccountName.text = it.data.name
                    binding.tvBottomsheetaccountEmail.text = it.data.email
                }
            }
        }

        viewModel.resultLogout.observe(viewLifecycleOwner) {
            when (it) {
                is UIState.Loading -> {
                    binding.btnBottomsheetaccountLogout.visibility = View.INVISIBLE
                    binding.pbBottomsheetaccount.visibility = View.VISIBLE
                }

                is UIState.Error -> {
                    binding.btnBottomsheetaccountLogout.visibility = View.VISIBLE
                    binding.pbBottomsheetaccount.visibility = View.GONE
                    Snackbar.make(requireView(), it.error.message.toString(), Snackbar.LENGTH_LONG)
                        .show()
                }

                is UIState.Success -> {
                    binding.btnBottomsheetaccountLogout.visibility = View.VISIBLE
                    binding.pbBottomsheetaccount.visibility = View.GONE
                }
            }
        }
    }

    private fun startShimmer() {
        binding.shimmerBottomsheetaccountProfile.startShimmer()
        binding.shimmerBottomsheetaccountProfile.visibility = View.VISIBLE
        binding.tvBottomsheetaccountName.visibility = View.INVISIBLE
        binding.tvBottomsheetaccountEmail.visibility = View.INVISIBLE
    }

    private fun stopShimmer() {
        binding.shimmerBottomsheetaccountProfile.stopShimmer()
        binding.shimmerBottomsheetaccountProfile.visibility = View.GONE
        binding.tvBottomsheetaccountName.visibility = View.VISIBLE
        binding.tvBottomsheetaccountEmail.visibility = View.VISIBLE

    }

    companion object {
        const val TAG = "ModalAccountBottomSheet"
    }
}