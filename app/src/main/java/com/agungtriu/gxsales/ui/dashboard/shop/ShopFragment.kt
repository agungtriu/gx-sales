package com.agungtriu.gxsales.ui.dashboard.shop

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.agungtriu.gxsales.base.BaseFragment
import com.agungtriu.gxsales.databinding.FragmentShopBinding

class ShopFragment : BaseFragment<FragmentShopBinding>(FragmentShopBinding::inflate) {
    private val viewModel: ShopViewModel by viewModels()
    private lateinit var adapter: ShopAdapter
    private var list: List<Product>? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ShopAdapter()
        binding.rvShop.layoutManager = LinearLayoutManager(requireContext())
        binding.rvShop.adapter = adapter
        setUpObserver()
        setUpListener()
    }

    private fun setUpListener() {
        binding.toolbarShop.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.tietShopSearch.addTextChangedListener { key ->
            val result = list?.filter { it.name.lowercase().contains(key.toString().lowercase()) }
            binding.layoutShopError.constraintError.isVisible = result.isNullOrEmpty()
            adapter.submitList(result)
        }
    }

    private fun setUpObserver() {
        viewModel.resultProducts.observe(viewLifecycleOwner) {
            list = it.products
            adapter.submitList(it.products)
        }
    }
}