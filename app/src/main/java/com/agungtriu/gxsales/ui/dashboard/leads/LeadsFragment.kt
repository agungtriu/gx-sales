package com.agungtriu.gxsales.ui.dashboard.leads

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.agungtriu.gxsales.base.BaseFragment
import com.agungtriu.gxsales.data.remote.response.LeadResponse
import com.agungtriu.gxsales.databinding.FragmentLeadsBinding
import com.agungtriu.gxsales.ui.dashboard.leads.filter.Filter
import com.agungtriu.gxsales.ui.dashboard.leads.filter.FilterBottomSheet
import com.agungtriu.gxsales.ui.dashboard.leads.filter.FilterBottomSheet.Companion.FILTER_KEY
import com.agungtriu.gxsales.ui.dashboard.leads.filter.FilterBottomSheet.Companion.RESULT_FILTER_KEY
import com.agungtriu.gxsales.utils.Date.dateToMillis
import com.agungtriu.gxsales.utils.UIState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeadsFragment : BaseFragment<FragmentLeadsBinding>(FragmentLeadsBinding::inflate) {
    private val viewModel: LeadsViewModel by viewModels()
    private lateinit var adapter: LeadsAdapter
    private lateinit var filterBottomSheet: FilterBottomSheet
    private var list = listOf<LeadResponse>()
    private lateinit var filter: Filter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = LeadsAdapter(requireActivity(), viewModel)
        binding.rvLeads.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLeads.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.rvLeads)

        setUpObserver()
        setUpListener()
    }

    private fun setUpListener() {
        binding.toolbarLeads.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnLeadsFilter.setOnClickListener {
            filterBottomSheet = FilterBottomSheet()
            filterBottomSheet.arguments =
                bundleOf(TO_FILTER_KEY to viewModel.filterSearch.toFilter())
            filterBottomSheet.show(childFragmentManager, FilterBottomSheet.TAG)
        }

        binding.tietLeadsSearch.addTextChangedListener { query ->
            if (query.isNullOrBlank()) {
                viewModel.filterSearch = FilterSearch(
                    search = null,
                    fromDate = viewModel.filterSearch.fromDate,
                    toDate = viewModel.filterSearch.toDate,
                    status = viewModel.filterSearch.status,
                    channel = viewModel.filterSearch.channel,
                    media = viewModel.filterSearch.media,
                    source = viewModel.filterSearch.source
                )
            } else {
                viewModel.filterSearch = FilterSearch(
                    search = query.toString(),
                    fromDate = viewModel.filterSearch.fromDate,
                    toDate = viewModel.filterSearch.toDate,
                    status = viewModel.filterSearch.status,
                    channel = viewModel.filterSearch.channel,
                    media = viewModel.filterSearch.media,
                    source = viewModel.filterSearch.source
                )
            }
            doFilter()
        }

        childFragmentManager.setFragmentResultListener(
            FILTER_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            filter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(RESULT_FILTER_KEY, Filter::class.java)
            } else {
                bundle.getParcelable(RESULT_FILTER_KEY)
            } ?: Filter()

            viewModel.filterSearch = FilterSearch(
                search = viewModel.filterSearch.search,
                fromDate = filter.fromDate,
                toDate = filter.toDate,
                status = filter.status,
                channel = filter.channel,
                media = filter.media,
                source = filter.source
            )
            doFilter()
        }
    }

    private fun doFilter() {
        var result = list
        if (viewModel.filterSearch.search != null) {
            result = list.filter {
                it.fullName != null &&
                    it.fullName.lowercase()
                        .contains((viewModel.filterSearch.search ?: "").lowercase())
            }
        }

        if (viewModel.filterSearch.fromDate != null && viewModel.filterSearch.toDate != null) {
            result = result.filter {
                it.createdAt != null &&
                    viewModel.filterSearch.fromDate!! <= dateToMillis(it.createdAt) &&
                    viewModel.filterSearch.toDate!! >= dateToMillis(it.createdAt)
            }
        }

        if (viewModel.filterSearch.status != null) {
            result = result.filter {
                it.status != null && it.status.name.equals(viewModel.filterSearch.status)
            }
        }

        if (viewModel.filterSearch.channel != null) {
            result = result.filter {
                it.channel != null && it.channel.name.equals(viewModel.filterSearch.channel)
            }
        }

        if (viewModel.filterSearch.media != null) {
            result = result.filter {
                it.media != null && it.media.name.equals(viewModel.filterSearch.media)
            }
        }

        if (viewModel.filterSearch.source != null) {
            result = result.filter {
                it.source != null && it.source.name.equals(viewModel.filterSearch.source)
            }
        }

        adapter.submitList(result)
    }

    private fun setUpObserver() {
        viewModel.resultLeads.observe(viewLifecycleOwner) {
            when (it) {
                is UIState.Loading -> startShimmer()
                is UIState.Error -> {
                    stopShimmer()
                    Snackbar.make(requireView(), it.error.message.toString(), Snackbar.LENGTH_LONG)
                        .show()
                }

                is UIState.Success -> {
                    stopShimmer()
                    list = it.data
                    adapter.submitList(list)
                }
            }
        }
        viewModel.resultDelete.observe(viewLifecycleOwner) {
            when (it) {
                is UIState.Loading -> startShimmer()
                is UIState.Error -> {
                    stopShimmer()
                    Snackbar.make(requireView(), it.error.message.toString(), Snackbar.LENGTH_LONG)
                        .show()
                }

                is UIState.Success -> viewModel.getLeads()
            }
        }
    }

    private fun startShimmer() {
        binding.rvLeads.visibility = View.GONE
        binding.shimmerLeads.visibility = View.VISIBLE
        binding.shimmerLeads.startShimmer()
    }

    private fun stopShimmer() {
        binding.rvLeads.visibility = View.VISIBLE
        binding.shimmerLeads.visibility = View.GONE
        binding.shimmerLeads.stopShimmer()
    }

    companion object {
        const val TO_FILTER_KEY = "ToFilter"
    }
}
