package com.agungtriu.gxsales.ui.dashboard.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.agungtriu.gxsales.base.BaseFragment
import com.agungtriu.gxsales.data.remote.response.StatusesItem
import com.agungtriu.gxsales.databinding.FragmentHomeBinding
import com.agungtriu.gxsales.utils.UIState
import com.agungtriu.gxsales.utils.Utils.currentMillis
import com.agungtriu.gxsales.utils.Utils.millisToDate
import com.agungtriu.gxsales.utils.Utils.millisToDisplayDate
import com.agungtriu.gxsales.utils.Utils.nextDayMillis
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.util.Pair as APair

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = HomeAdapter()
        binding.rvHome.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvHome.adapter = adapter
        binding.btnHomeDate.text = millisToDisplayDate(currentMillis())
            .plus(" - ")
            .plus(millisToDisplayDate(nextDayMillis(7)))
        setUpObserver()
        setUpListener()
    }

    @SuppressLint("SetTextI18n")
    private fun setUpListener() {
        binding.btnHomeDate.setOnClickListener {
            val datePicker = MaterialDatePicker
                .Builder
                .dateRangePicker()
                .setTitleText("Select dates")
                .setSelection(
                    APair(
                        MaterialDatePicker.todayInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds()
                    )
                ).build()
            datePicker.show(childFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                binding.btnHomeDate.text =
                    millisToDisplayDate(datePicker.selection?.first ?: currentMillis())
                        .plus(" - ")
                        .plus(millisToDisplayDate(datePicker.selection?.second ?: nextDayMillis(7)))
                viewModel.getDashboard(
                    fromDate = millisToDate(datePicker.selection?.first ?: currentMillis()),
                    toDate = millisToDate(datePicker.selection?.second ?: nextDayMillis(7))
                )
            }
        }
    }

    private fun setUpObserver() {
        viewModel.resultProfile.observe(viewLifecycleOwner) {
            when (it) {
                is UIState.Loading -> startShimmerProfile()
                is UIState.Error -> {
                    Snackbar.make(requireView(), it.error.message.toString(), Snackbar.LENGTH_LONG)
                        .show()
                    stopShimmerProfile()
                }

                is UIState.Success -> {
                    stopShimmerProfile()
                    binding.tvHomeName.text = it.data.name
                    binding.tvHomeEmail.text = it.data.email
                }
            }
        }

        viewModel.resultDashboard.observe(viewLifecycleOwner) {
            when (it) {
                is UIState.Loading -> startShimmerDashboard()
                is UIState.Error -> {
                    Snackbar.make(requireView(), it.error.message.toString(), Snackbar.LENGTH_LONG)
                        .show()
                    stopShimmerDashboard()
                }

                is UIState.Success -> {
                    stopShimmerDashboard()
                    val result = mutableListOf(StatusesItem(total = it.data.total, name = null))
                    if (!it.data.statuses.isNullOrEmpty()) {
                        result.addAll(it.data.statuses)
                        adapter.submitList(result)
                    }
                }
            }
        }


    }

    private fun startShimmerProfile() {
        binding.shimmerHomeProfile.startShimmer()
        binding.shimmerHomeProfile.visibility = View.VISIBLE
        binding.tvHomeName.visibility = View.INVISIBLE
        binding.tvHomeEmail.visibility = View.INVISIBLE
    }


    private fun stopShimmerProfile() {
        binding.shimmerHomeProfile.stopShimmer()
        binding.shimmerHomeProfile.visibility = View.GONE
        binding.tvHomeName.visibility = View.VISIBLE
        binding.tvHomeEmail.visibility = View.VISIBLE
    }

    private fun startShimmerDashboard() {
        binding.shimmerHome.startShimmer()
        binding.shimmerHome.visibility = View.VISIBLE
        binding.rvHome.visibility = View.INVISIBLE
    }

    private fun stopShimmerDashboard() {
        binding.shimmerHome.stopShimmer()
        binding.shimmerHome.visibility = View.GONE
        binding.rvHome.visibility = View.VISIBLE
    }
}