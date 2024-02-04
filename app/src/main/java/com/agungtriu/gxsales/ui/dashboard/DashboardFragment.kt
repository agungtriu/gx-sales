package com.agungtriu.gxsales.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.agungtriu.gxsales.R
import com.agungtriu.gxsales.base.BaseFragment
import com.agungtriu.gxsales.databinding.FragmentDashboardBinding
import com.agungtriu.gxsales.ui.dashboard.account.AccountBottomSheet
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardFragment :
    BaseFragment<FragmentDashboardBinding>(FragmentDashboardBinding::inflate) {
    private val viewModel: DashBoardViewModel by viewModels()
    private lateinit var accountBottomSheet: AccountBottomSheet
    private val navHostFragment by lazy {
        childFragmentManager.findFragmentById(R.id.fcv_main_fragment) as NavHostFragment
    }

    private val navController by lazy {
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (!viewModel.getStatus()) {
            findNavController().navigate(R.id.action_dashboardFragment_to_loginFragment)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bnvDashboard.setupWithNavController(navController)

        viewModel.getAuthorization().observe(viewLifecycleOwner) {
            if (!viewModel.getStatus()) {
                findNavController().navigate(R.id.action_dashboardFragment_to_loginFragment)
            }
        }

        binding.bnvDashboard.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.action_dashboard_to_home)
                    true
                }

                R.id.leadsFragment -> {
                    navController.navigate(R.id.action_dashboard_to_leads)
                    true
                }

                R.id.shopFragment -> {
                    navController.navigate(R.id.action_dashboard_to_shop)
                    true
                }

                R.id.accountFragment -> {
                    accountBottomSheet = AccountBottomSheet()
                    accountBottomSheet.show(childFragmentManager, AccountBottomSheet.TAG)
                    false
                }

                else -> false
            }
        }
    }
}