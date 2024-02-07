package com.agungtriu.gxsales.ui.dashboard.location

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.agungtriu.gxsales.R
import com.agungtriu.gxsales.base.BaseFragment
import com.agungtriu.gxsales.databinding.FragmentLocationBinding
import com.agungtriu.gxsales.utils.MapsManager
import com.agungtriu.gxsales.utils.Utils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationFragment : BaseFragment<FragmentLocationBinding>(FragmentLocationBinding::inflate) {
    private lateinit var map: GoogleMap
    private val viewModel: LocationViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var dataLocation: Location? = null

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isScrollGesturesEnabled = true
        googleMap.uiSettings.isZoomGesturesEnabled = true
        googleMap.uiSettings.isMyLocationButtonEnabled = false
        getMyLocation()

        googleMap.setOnMapLongClickListener { latLng ->
            viewModel.setLocation(requireContext(), latLng.latitude, latLng.longitude)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        setUpListener()
        setUpObserver()
    }

    private fun setUpObserver() {
        viewModel.savedLocation.observe(viewLifecycleOwner) {
            if (it.address == null || it.latitude == null || it.longitude == null) {
                binding.btnLocationSelect.visibility = View.GONE
                binding.tietLocationSearch.text = null
                map.clear()
                dataLocation = null
            } else {
                setCheckPoint(it.latitude, it.longitude)
                binding.tietLocationSearch.setText(it.address)
                binding.btnLocationSelect.visibility = View.VISIBLE
                Utils.closeSoftKeyboard(requireView(), requireContext())
                dataLocation = it
            }
        }
    }

    private fun setUpListener() {
        binding.btnLocationBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnLocationReset.setOnClickListener {
            viewModel.resetLocation()
            Utils.closeSoftKeyboard(requireView(), requireContext())
        }

        binding.btnLocationSelect.setOnClickListener {
            val savedStateHandle = findNavController().previousBackStackEntry?.savedStateHandle
            savedStateHandle?.set(LOCATION_KEY, dataLocation)
            findNavController().navigateUp()
        }

        binding.tietLocationSearch.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || (keyEvent != null && keyEvent.action == KeyEvent.ACTION_DOWN && keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) {
                val latLng =
                    MapsManager.getLocationFromAddress(requireContext(), textView.text.toString())
                if (latLng != null) {
                    viewModel.setLocation(requireContext(), latLng.first, latLng.second)
                    map.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                latLng.first,
                                latLng.second
                            ), 15f
                        )
                    )
                }
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext().applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        map.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    location.latitude,
                                    location.longitude
                                ), 15f
                            )
                        )
                    }
                }
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun setCheckPoint(latitude: Double, longitude: Double) {
        map.clear()
        map.addMarker(
            MarkerOptions()
                .position(LatLng(latitude, longitude))
                .snippet("Lat: $latitude Long: $longitude")
                .icon(drawableToBitmap(R.drawable.ic_home_map))
        )
    }

    companion object {
        const val LOCATION_KEY = "Location"
    }

    private fun drawableToBitmap(@DrawableRes id: Int): BitmapDescriptor {
        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, id)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}