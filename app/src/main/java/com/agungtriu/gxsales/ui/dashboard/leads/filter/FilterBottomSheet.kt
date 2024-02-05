package com.agungtriu.gxsales.ui.dashboard.leads.filter

import android.os.Build
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.util.Pair
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.agungtriu.gxsales.R
import com.agungtriu.gxsales.databinding.BottomSheetFilterBinding
import com.agungtriu.gxsales.ui.dashboard.leads.LeadsFragment.Companion.TO_FILTER_KEY
import com.agungtriu.gxsales.utils.UIState
import com.agungtriu.gxsales.utils.Utils
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class FilterBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetFilterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FilterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val contextThemeWrapper = ContextThemeWrapper(requireActivity(), R.style.Theme_GXSales)
        _binding = BottomSheetFilterBinding.inflate(
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
        setUpObserver()
        setUpListener()
    }

    private fun setUpListener() {
        binding.btnBottomsheetfilterDate.setOnClickListener {
            val datePicker = MaterialDatePicker
                .Builder
                .dateRangePicker()
                .setTitleText("Select dates")
                .setSelection(
                    Pair(
                        MaterialDatePicker.todayInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds()
                    )
                ).build()
            datePicker.show(childFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                displayDate(datePicker.selection?.first, datePicker.selection?.second)

                viewModel.filter = Filter(
                    fromDate = datePicker.selection?.first?.minus(TimeUnit.HOURS.toMillis(7)),
                    toDate = datePicker.selection?.second?.plus(TimeUnit.HOURS.toMillis(17)),
                    status = viewModel.filter.status,
                    channel = viewModel.filter.channel,
                    media = viewModel.filter.channel,
                    source = viewModel.filter.source
                )
            }
        }

        binding.tvBottomsheetfilterStatus.addTextChangedListener {
            if (!it.isNullOrBlank()) {
                viewModel.filter = Filter(
                    fromDate = viewModel.filter.fromDate,
                    toDate = viewModel.filter.toDate,
                    status = viewModel.filter.status,
                    channel = it.toString(),
                    media = viewModel.filter.media,
                    source = viewModel.filter.source
                )
            }
        }

        binding.tvBottomsheetfilterChannel.addTextChangedListener {
            if (!it.isNullOrBlank()) {
                viewModel.filter = Filter(
                    fromDate = viewModel.filter.fromDate,
                    toDate = viewModel.filter.toDate,
                    status = viewModel.filter.status,
                    channel = it.toString(),
                    media = viewModel.filter.media,
                    source = viewModel.filter.source
                )
            }
        }

        binding.tvBottomsheetfilterMedia.addTextChangedListener {
            if (!it.isNullOrBlank()) {
                viewModel.filter = Filter(
                    fromDate = viewModel.filter.fromDate,
                    toDate = viewModel.filter.toDate,
                    status = viewModel.filter.status,
                    channel = viewModel.filter.channel,
                    media = it.toString(),
                    source = viewModel.filter.source
                )
            }
        }

        binding.tvBottomsheetfilterSource.addTextChangedListener {
            if (!it.isNullOrBlank()) {
                viewModel.filter = Filter(
                    fromDate = viewModel.filter.fromDate,
                    toDate = viewModel.filter.toDate,
                    status = viewModel.filter.status,
                    channel = viewModel.filter.channel,
                    media = viewModel.filter.media,
                    source = it.toString()
                )
            }
        }

        binding.btnBottomsheetfilterReset.setOnClickListener {
            setFragmentResult(FILTER_KEY, bundleOf(RESULT_FILTER_KEY to Filter()))
            dismiss()
        }

        binding.btnBottomsheetfilterSearch.setOnClickListener {
            setFragmentResult(FILTER_KEY, bundleOf(RESULT_FILTER_KEY to viewModel.filter))
            dismiss()
        }
    }

    private fun displayDate(first: Long?, second: Long?) {
        if (first != null && second != null) {
            binding.btnBottomsheetfilterDate.text =
                Utils.millisToDate(first)
                    .plus(" - ")
                    .plus(Utils.millisToDate(second))

        } else {
            binding.btnBottomsheetfilterDate.text = getString(R.string.hint_range_date)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.filter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(TO_FILTER_KEY, Filter::class.java)
        } else {
            arguments?.getParcelable(TO_FILTER_KEY)
        } ?: Filter()

        displayDate(viewModel.filter.fromDate, viewModel.filter.toDate)
        binding.tvBottomsheetfilterStatus.setText(viewModel.filter.status)
        binding.tvBottomsheetfilterChannel.setText(viewModel.filter.channel)
        binding.tvBottomsheetfilterMedia.setText(viewModel.filter.media)
        binding.tvBottomsheetfilterSource.setText(viewModel.filter.source)
    }

    private fun setUpObserver() {
        viewModel.resultStatuses.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> startShimmer(
                    binding.shimmerBottomsheetfilterStatus,
                    binding.tilBottomsheetfilterStatus
                )

                is UIState.Error -> {
                    stopShimmer(
                        binding.shimmerBottomsheetfilterStatus,
                        binding.tilBottomsheetfilterStatus
                    )
                    binding.tvBottomsheetfilterStatusTitle.visibility = View.GONE
                    binding.tilBottomsheetfilterStatus.visibility = View.GONE
                }

                is UIState.Success -> {
                    stopShimmer(
                        binding.shimmerBottomsheetfilterStatus,
                        binding.tilBottomsheetfilterStatus
                    )
                    (binding.tvBottomsheetfilterStatus as MaterialAutoCompleteTextView)
                        .setSimpleItems(state.data.map { it.name }.toTypedArray())
                }
            }
        }

        viewModel.resultChannels.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> startShimmer(
                    binding.shimmerBottomsheetfilterChannel,
                    binding.tilBottomsheetfilterChannel
                )

                is UIState.Error -> {
                    stopShimmer(
                        binding.shimmerBottomsheetfilterChannel,
                        binding.tilBottomsheetfilterChannel
                    )
                    binding.tvBottomsheetfilterChannelTitle.visibility = View.GONE
                    binding.tilBottomsheetfilterChannel.visibility = View.GONE
                }

                is UIState.Success -> {
                    stopShimmer(
                        binding.shimmerBottomsheetfilterChannel,
                        binding.tilBottomsheetfilterChannel
                    )
                    (binding.tvBottomsheetfilterChannel as MaterialAutoCompleteTextView)
                        .setSimpleItems(state.data.map { it.name }.toTypedArray())
                }
            }
        }

        viewModel.resultMedias.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> startShimmer(
                    binding.shimmerBottomsheetfilterMedia,
                    binding.tilBottomsheetfilterMedia
                )

                is UIState.Error -> {
                    stopShimmer(
                        binding.shimmerBottomsheetfilterMedia,
                        binding.tilBottomsheetfilterMedia
                    )
                    binding.tvBottomsheetfilterMediaTitle.visibility = View.GONE
                    binding.tilBottomsheetfilterMedia.visibility = View.GONE
                }

                is UIState.Success -> {
                    stopShimmer(
                        binding.shimmerBottomsheetfilterMedia,
                        binding.tilBottomsheetfilterMedia
                    )
                    (binding.tvBottomsheetfilterMedia as MaterialAutoCompleteTextView)
                        .setSimpleItems(state.data.map { it.name }.toTypedArray())
                }
            }
        }

        viewModel.resultSources.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> startShimmer(
                    binding.shimmerBottomsheetfilterSource,
                    binding.tilBottomsheetfilterSource
                )

                is UIState.Error -> {
                    stopShimmer(
                        binding.shimmerBottomsheetfilterSource,
                        binding.tilBottomsheetfilterSource
                    )
                    binding.tvBottomsheetfilterSourceTitle.visibility = View.GONE
                    binding.tilBottomsheetfilterSource.visibility = View.GONE
                }

                is UIState.Success -> {
                    stopShimmer(
                        binding.shimmerBottomsheetfilterSource,
                        binding.tilBottomsheetfilterSource
                    )
                    (binding.tvBottomsheetfilterSource as MaterialAutoCompleteTextView)
                        .setSimpleItems(state.data.map { it.name }.toTypedArray())
                }
            }
        }
    }

    private fun startShimmer(shimmer: ShimmerFrameLayout, layout: TextInputLayout) {
        shimmer.startShimmer()
        shimmer.visibility = View.VISIBLE
        layout.visibility = View.INVISIBLE
    }

    private fun stopShimmer(shimmer: ShimmerFrameLayout, layout: TextInputLayout) {
        shimmer.stopShimmer()
        shimmer.visibility = View.GONE
        layout.visibility = View.VISIBLE

    }

    companion object {
        const val TAG = "ModalFilterBottomSheet"
        const val FILTER_KEY = "Filter"
        const val RESULT_FILTER_KEY = "ResultFilter"
    }
}