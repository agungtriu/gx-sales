package com.agungtriu.gxsales.ui.dashboard.addlead

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.agungtriu.gxsales.R
import com.agungtriu.gxsales.base.BaseFragment
import com.agungtriu.gxsales.databinding.FragmentAddLeadBinding
import com.agungtriu.gxsales.databinding.LayoutImageBinding
import com.agungtriu.gxsales.ui.dashboard.location.Location
import com.agungtriu.gxsales.ui.dashboard.location.LocationFragment.Companion.LOCATION_KEY
import com.agungtriu.gxsales.utils.FormValidation.dropDownInputToString
import com.agungtriu.gxsales.utils.FormValidation.textInputToString
import com.agungtriu.gxsales.utils.PhotoUriManager
import com.agungtriu.gxsales.utils.PhotoUriManager.getFileSize
import com.agungtriu.gxsales.utils.PhotoUriManager.getImageName
import com.agungtriu.gxsales.utils.UIState
import com.agungtriu.gxsales.utils.Utils
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddLeadFragment : BaseFragment<FragmentAddLeadBinding>(FragmentAddLeadBinding::inflate) {
    private val viewModel: AddLeadViewModel by viewModels()
    private lateinit var galleryResultLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var takePictureLauncher: ActivityResultLauncher<Uri>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.id != null) {
            binding.layoutAddForm.toolbarForm.title = getString(R.string.form_update_lead)
        }
        setUpBackStack()
        registerActivityResult()
        setUpObserver()
        setUpListener()
    }

    private fun setUpBackStack() {
        val currentBackStackEntry = findNavController().currentBackStackEntry
        val savedStateHandle = currentBackStackEntry?.savedStateHandle
        savedStateHandle?.getLiveData<Location>(LOCATION_KEY)
            ?.observe(
                currentBackStackEntry,
                Observer { result ->
                    viewModel.setLocation(result)
                }
            )
    }

    private fun setUpListener() {
        binding.layoutAddForm.toolbarForm.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.layoutAddForm.btnFormNext.setOnClickListener {
            submit()
        }
        binding.layoutAddForm.tietFormPhone.addTextChangedListener {
            if (!it.isNullOrBlank() && it[0] == '0') {
                if (it.length > 1) {
                    binding.layoutAddForm.tietFormPhone.setText(it.substring(1))
                } else {
                    binding.layoutAddForm.tietFormPhone.text = null
                }
            }
        }
        binding.layoutAddForm.consFormAddPhotoId.setOnClickListener {
            showDialog()
        }
        binding.layoutAddForm.btnFormShow.setOnClickListener {
            imageUri?.let { showImageDialog(it) }
        }
        binding.layoutAddForm.btnFormEdit.setOnClickListener {
            showDialog()
        }
        binding.layoutAddForm.btnFormRemove.setOnClickListener {
            imageUri = null
            binding.layoutAddForm.consFormAddPhotoId.visibility = View.VISIBLE
            binding.layoutAddForm.consFormAddPhotoIdDisplay.visibility = View.GONE
        }
        binding.layoutAddForm.btnFormSelectLocation.setOnClickListener {
            findNavController().navigate(R.id.action_addLeadFragment_to_locationFragment)
        }

        binding.layoutAddForm.tvFormMale.setOnClickListener {
            binding.layoutAddForm.rbFormMale.isChecked = true
        }

        binding.layoutAddForm.tvFormFemale.setOnClickListener {
            binding.layoutAddForm.rbFormFemale.isChecked = true
        }
    }


    private fun setUpObserver() {
        viewModel.resultBranchOffices.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> Utils.startShimmerInput(
                    binding.layoutAddForm.shimmerFormBranch,
                    binding.layoutAddForm.tilFormBranch
                )

                is UIState.Error -> Utils.stopShimmerInput(
                    binding.layoutAddForm.shimmerFormBranch,
                    binding.layoutAddForm.tilFormBranch
                )


                is UIState.Success -> {
                    viewModel.branchOffices = state.data
                    Utils.stopShimmerInput(
                        binding.layoutAddForm.shimmerFormBranch,
                        binding.layoutAddForm.tilFormBranch
                    )
                    (binding.layoutAddForm.tvFormBranch as MaterialAutoCompleteTextView)
                        .setSimpleItems(state.data.map { it.name }.toTypedArray())
                }
            }
        }

        viewModel.resultLocation.observe(viewLifecycleOwner) {
            if (!it.address.isNullOrBlank() && it.latitude != null && it.longitude != null) {
                binding.layoutAddForm.tietFormAddress.setText(it.address)
                binding.layoutAddForm.tietFormLatitude.setText(String.format("%.5f", it.latitude))
                binding.layoutAddForm.tietFormLongitude.setText(String.format("%.5f", it.longitude))
                viewModel.dataLocation = it
            }
        }

        viewModel.resultLead.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> {
                    Utils.startShimmerInput(
                        binding.layoutAddForm.shimmerFormBranch,
                        binding.layoutAddForm.tilFormBranch
                    )
                    Utils.startShimmerInput(
                        binding.layoutAddForm.shimmerFormName,
                        binding.layoutAddForm.tilFormName
                    )
                    Utils.startShimmerInput(
                        binding.layoutAddForm.shimmerFormEmail,
                        binding.layoutAddForm.tilFormEmail
                    )
                    Utils.startShimmerInput(
                        binding.layoutAddForm.shimmerFormPhone,
                        binding.layoutAddForm.tilFormPhone
                    )
                    Utils.startShimmerInput(
                        binding.layoutAddForm.shimmerFormAddress,
                        binding.layoutAddForm.tilFormAddress
                    )
                    Utils.startShimmerInput(
                        binding.layoutAddForm.shimmerFormLatitude,
                        binding.layoutAddForm.tilFormLatitude
                    )

                    Utils.startShimmerInput(
                        binding.layoutAddForm.shimmerFormLongitude,
                        binding.layoutAddForm.tilFormLongitude
                    )

                    binding.layoutAddForm.shimmerFormGender.startShimmer()
                    binding.layoutAddForm.shimmerFormGender.visibility = View.VISIBLE
                    binding.layoutAddForm.rgFormGender.visibility = View.INVISIBLE

                    Utils.startShimmerInput(
                        binding.layoutAddForm.shimmerFormIdNumber,
                        binding.layoutAddForm.tilFormIdNumber
                    )
                }

                is UIState.Error -> {
                    Utils.stopShimmerInput(
                        binding.layoutAddForm.shimmerFormBranch,
                        binding.layoutAddForm.tilFormBranch
                    )
                    Utils.stopShimmerInput(
                        binding.layoutAddForm.shimmerFormName,
                        binding.layoutAddForm.tilFormName
                    )
                    Utils.stopShimmerInput(
                        binding.layoutAddForm.shimmerFormEmail,
                        binding.layoutAddForm.tilFormEmail
                    )
                    Utils.stopShimmerInput(
                        binding.layoutAddForm.shimmerFormPhone,
                        binding.layoutAddForm.tilFormPhone
                    )
                    Utils.stopShimmerInput(
                        binding.layoutAddForm.shimmerFormAddress,
                        binding.layoutAddForm.tilFormAddress
                    )
                    Utils.stopShimmerInput(
                        binding.layoutAddForm.shimmerFormLatitude,
                        binding.layoutAddForm.tilFormLatitude
                    )

                    Utils.stopShimmerInput(
                        binding.layoutAddForm.shimmerFormLongitude,
                        binding.layoutAddForm.tilFormLongitude
                    )

                    binding.layoutAddForm.shimmerFormGender.stopShimmer()
                    binding.layoutAddForm.shimmerFormGender.visibility = View.GONE
                    binding.layoutAddForm.rgFormGender.visibility = View.VISIBLE

                    Utils.stopShimmerInput(
                        binding.layoutAddForm.shimmerFormIdNumber,
                        binding.layoutAddForm.tilFormIdNumber
                    )
                }


                is UIState.Success -> {
                    viewModel.lead = state.data
                    viewModel.setLocation(
                        Location(
                            state.data.address,
                            state.data.latitude?.toDouble(),
                            state.data.longitude?.toDouble()
                        )
                    )
                    binding.layoutAddForm.tvFormBranch.setText(state.data.branchOffice?.name)
                    binding.layoutAddForm.tietFormName.setText(state.data.fullName)
                    binding.layoutAddForm.tietFormEmail.setText(state.data.email)
                    binding.layoutAddForm.tietFormPhone.setText(state.data.phone)
                    binding.layoutAddForm.tietFormAddress.setText(state.data.address)
                    when (state.data.gender) {
                        "male" -> binding.layoutAddForm.rbFormMale.isChecked = true
                        "female" -> binding.layoutAddForm.rbFormFemale.isChecked = true
                    }
                    binding.layoutAddForm.tietFormIdNumber.setText(state.data.iDNumber)

                    Utils.stopShimmerInput(
                        binding.layoutAddForm.shimmerFormBranch,
                        binding.layoutAddForm.tilFormBranch
                    )
                    Utils.stopShimmerInput(
                        binding.layoutAddForm.shimmerFormName,
                        binding.layoutAddForm.tilFormName
                    )
                    Utils.stopShimmerInput(
                        binding.layoutAddForm.shimmerFormEmail,
                        binding.layoutAddForm.tilFormEmail
                    )
                    Utils.stopShimmerInput(
                        binding.layoutAddForm.shimmerFormPhone,
                        binding.layoutAddForm.tilFormPhone
                    )
                    Utils.stopShimmerInput(
                        binding.layoutAddForm.shimmerFormAddress,
                        binding.layoutAddForm.tilFormAddress
                    )
                    Utils.stopShimmerInput(
                        binding.layoutAddForm.shimmerFormLatitude,
                        binding.layoutAddForm.tilFormLatitude
                    )

                    Utils.stopShimmerInput(
                        binding.layoutAddForm.shimmerFormLongitude,
                        binding.layoutAddForm.tilFormLongitude
                    )

                    binding.layoutAddForm.shimmerFormGender.stopShimmer()
                    binding.layoutAddForm.shimmerFormGender.visibility = View.GONE
                    binding.layoutAddForm.rgFormGender.visibility = View.VISIBLE

                    Utils.stopShimmerInput(
                        binding.layoutAddForm.shimmerFormIdNumber,
                        binding.layoutAddForm.tilFormIdNumber
                    )
                }
            }
        }
    }

    private var imageUri: Uri? = null

    private fun registerActivityResult() {
        takePictureLauncher = registerForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { isSuccess ->
            if (isSuccess) {
                imageUri?.let { displayUpload(it) }
            }
        }

        galleryResultLauncher = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) { uri: Uri? ->
            if (uri != null) {
                imageUri = uri
                imageUri?.let { displayUpload(it, getImageName(it, requireContext())) }
            }
        }
    }

    private fun showImageDialog(imageUrl: Uri) {
        val dialogBinding = LayoutImageBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .create()
        Glide.with(dialog.context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_image)
            .into(dialogBinding.ivDialog)

        dialog.show()
    }

    private fun showDialog() {
        val items = arrayOf(
            getString(R.string.dialog_form_camera),
            getString(R.string.dialog_form_gallery)
        )
        val dialogProfile =
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.dialog_form_choose_image))
                .setItems(items) { dialog, which ->
                    when (items[which]) {
                        getString(R.string.dialog_form_gallery) -> {
                            openGallery()
                            dialog.dismiss()
                        }

                        getString(R.string.dialog_form_camera) -> {
                            openCamera()
                            dialog.dismiss()
                        }
                    }
                }
        dialogProfile.show()
    }

    private fun openCamera() {
        imageUri = PhotoUriManager.buildNewUri(requireContext())
        takePictureLauncher.launch(imageUri)
    }

    private fun openGallery() {
        galleryResultLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun displayUpload(imageUri: Uri, imageName: String? = null) {
        binding.layoutAddForm.consFormAddPhotoId.visibility = View.GONE
        binding.layoutAddForm.consFormAddPhotoIdDisplay.visibility = View.VISIBLE
        binding.layoutAddForm.tvFormAddPhotoIdDisplayFilename.text =
            imageName ?: imageUri.lastPathSegment
        binding.layoutAddForm.tvFormAddPhotoIdDisplaySize.text =
            getFileSize(requireContext(), imageUri)
    }

    private fun submit() {

        val fullName = textInputToString(
            binding.layoutAddForm.tietFormName,
            binding.layoutAddForm.tvFormNameTitle,
            false,
            requireContext()
        )
        val email = textInputToString(
            binding.layoutAddForm.tietFormEmail,
            binding.layoutAddForm.tvFormEmailTitle,
            true,
            requireContext(),
            "email"
        )
        val phone = textInputToString(
            binding.layoutAddForm.tietFormPhone,
            binding.layoutAddForm.tvFormPhoneTitle,
            false,
            requireContext(),
            "phone"
        )
        val address = textInputToString(
            binding.layoutAddForm.tietFormAddress,
            binding.layoutAddForm.tvFormAddressTitle,
            false,
            requireContext()
        )
        textInputToString(
            binding.layoutAddForm.tietFormLatitude,
            binding.layoutAddForm.tvFormLatitudeTitle,
            false,
            requireContext()
        )
        textInputToString(
            binding.layoutAddForm.tietFormLongitude,
            binding.layoutAddForm.tvFormLongitudeTitle,
            false,
            requireContext()
        )
        val gender = if (binding.layoutAddForm.rgFormGender.checkedRadioButtonId != -1) {
            requireView().findViewById<RadioButton>(binding.layoutAddForm.rgFormGender.checkedRadioButtonId).text.toString()
                .lowercase()
        } else null
        val idNumber = textInputToString(
            binding.layoutAddForm.tietFormIdNumber,
            binding.layoutAddForm.tvFormIdNumberTitle,
            true,
            requireContext()
        )
        val branchOfficeId = dropDownInputToString(
            binding.layoutAddForm.tvFormBranch,
            viewModel.branchOffices,
            binding.layoutAddForm.tvFormBranchTitle,
            false
        )

        if (branchOfficeId != null && fullName != null && phone != null && address != null && viewModel.dataLocation.latitude != null && viewModel.dataLocation.longitude != null) {
            val bundle = bundleOf(
                FORM_KEY to Form(
                    fullName = fullName,
                    email = email,
                    phone = phone,
                    address = address,
                    latitude = viewModel.dataLocation.latitude.toString(),
                    longitude = viewModel.dataLocation.longitude.toString(),
                    companyName = "Global Xtreme",
                    gender = gender,
                    idNumber = idNumber,
                    branchOfficeId = branchOfficeId,
                    imageUrl = imageUri
                ),
                UPDATE_KEY to viewModel.lead
            )
            findNavController().navigate(
                R.id.action_addLeadFragment_to_addLeadSubmitFragment,
                bundle
            )
        }
    }

    companion object {
        const val FORM_KEY = "FromKey"
        const val UPDATE_KEY = "UpdateKey"
    }
}