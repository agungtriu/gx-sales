package com.agungtriu.gxsales.ui.dashboard.addleadsubmit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.agungtriu.gxsales.R
import com.agungtriu.gxsales.base.BaseFragment
import com.agungtriu.gxsales.data.remote.request.PostLeadRequest
import com.agungtriu.gxsales.data.remote.request.UpdateStatusRequest
import com.agungtriu.gxsales.databinding.FragmentAddLeadSubmitBinding
import com.agungtriu.gxsales.databinding.LayoutDialogSubmitBinding
import com.agungtriu.gxsales.utils.FormValidation
import com.agungtriu.gxsales.utils.FormValidation.dropDownInputToMultipart
import com.agungtriu.gxsales.utils.FormValidation.dropDownInputToString
import com.agungtriu.gxsales.utils.FormValidation.stringToMultipart
import com.agungtriu.gxsales.utils.FormValidation.textInputToMultipart
import com.agungtriu.gxsales.utils.UIState
import com.agungtriu.gxsales.utils.Utils.startShimmerInput
import com.agungtriu.gxsales.utils.Utils.stopShimmerInput
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody

@AndroidEntryPoint
class AddLeadSubmitFragment :
    BaseFragment<FragmentAddLeadSubmitBinding>(FragmentAddLeadSubmitBinding::inflate) {
    private val viewModel: AddLeadSubmitViewModel by viewModels()
    var statusId: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.lead != null) {
            binding.layoutAddleadsubmit.toolbarFormsecond.title =
                getString(R.string.form_update_lead)
            binding.layoutAddleadsubmit.tvFormsecondType.setText(viewModel.lead?.type?.name, false)
            binding.layoutAddleadsubmit.tvFormsecondChannel.setText(viewModel.lead?.channel?.name, false)
            binding.layoutAddleadsubmit.tvFormsecondMedia.setText(viewModel.lead?.media?.name, false)
            binding.layoutAddleadsubmit.tvFormsecondSource.setText(viewModel.lead?.source?.name, false)
            binding.layoutAddleadsubmit.tvFormsecondStatus.setText(viewModel.lead?.status?.name, false)
            binding.layoutAddleadsubmit.tvFormsecondProbability.setText(viewModel.lead?.probability?.name, false)
            binding.layoutAddleadsubmit.tietFormsecondNote.setText(viewModel.lead?.generalNotes)
        }

        setUpObserver()
        setUpListener()
    }

    private fun setUpListener() {
        binding.layoutAddleadsubmit.toolbarFormsecond.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.layoutAddleadsubmit.btnFormsecondPrevious.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.layoutAddleadsubmit.btnFormsecondSubmit.setOnClickListener {
            submit()
        }
    }

    private fun setUpObserver() {
        viewModel.resultTypes.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> startShimmerInput(
                    binding.layoutAddleadsubmit.shimmerFormsecondType,
                    binding.layoutAddleadsubmit.tilFormsecondType
                )

                is UIState.Error -> stopShimmerInput(
                    binding.layoutAddleadsubmit.shimmerFormsecondType,
                    binding.layoutAddleadsubmit.tilFormsecondType
                )


                is UIState.Success -> {
                    viewModel.types = state.data
                    stopShimmerInput(
                        binding.layoutAddleadsubmit.shimmerFormsecondType,
                        binding.layoutAddleadsubmit.tilFormsecondType
                    )
                    (binding.layoutAddleadsubmit.tvFormsecondType as MaterialAutoCompleteTextView)
                        .setSimpleItems(state.data.map { it.name }.toTypedArray())
                }
            }
        }

        viewModel.resultChannels.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> startShimmerInput(
                    binding.layoutAddleadsubmit.shimmerFormsecondChannel,
                    binding.layoutAddleadsubmit.tilFormsecondChannel
                )

                is UIState.Error -> stopShimmerInput(
                    binding.layoutAddleadsubmit.shimmerFormsecondChannel,
                    binding.layoutAddleadsubmit.tilFormsecondChannel
                )

                is UIState.Success -> {
                    viewModel.channels = state.data
                    stopShimmerInput(
                        binding.layoutAddleadsubmit.shimmerFormsecondChannel,
                        binding.layoutAddleadsubmit.tilFormsecondChannel
                    )
                    (binding.layoutAddleadsubmit.tvFormsecondChannel as MaterialAutoCompleteTextView)
                        .setSimpleItems(state.data.map { it.name }.toTypedArray())
                }
            }
        }

        viewModel.resultMedias.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> startShimmerInput(
                    binding.layoutAddleadsubmit.shimmerFormsecondMedia,
                    binding.layoutAddleadsubmit.tilFormsecondMedia
                )

                is UIState.Error -> stopShimmerInput(
                    binding.layoutAddleadsubmit.shimmerFormsecondMedia,
                    binding.layoutAddleadsubmit.tilFormsecondMedia
                )

                is UIState.Success -> {
                    viewModel.medias = state.data
                    stopShimmerInput(
                        binding.layoutAddleadsubmit.shimmerFormsecondMedia,
                        binding.layoutAddleadsubmit.tilFormsecondMedia
                    )
                    (binding.layoutAddleadsubmit.tvFormsecondMedia as MaterialAutoCompleteTextView)
                        .setSimpleItems(state.data.map { it.name }.toTypedArray())
                }
            }
        }

        viewModel.resultSources.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> startShimmerInput(
                    binding.layoutAddleadsubmit.shimmerFormsecondSource,
                    binding.layoutAddleadsubmit.tilFormsecondSource
                )

                is UIState.Error -> stopShimmerInput(
                    binding.layoutAddleadsubmit.shimmerFormsecondSource,
                    binding.layoutAddleadsubmit.tilFormsecondSource
                )

                is UIState.Success -> {
                    viewModel.sources = state.data
                    stopShimmerInput(
                        binding.layoutAddleadsubmit.shimmerFormsecondSource,
                        binding.layoutAddleadsubmit.tilFormsecondSource
                    )
                    (binding.layoutAddleadsubmit.tvFormsecondSource as MaterialAutoCompleteTextView)
                        .setSimpleItems(state.data.map { it.name }.toTypedArray())
                }
            }
        }

        viewModel.resultStatuses.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> startShimmerInput(
                    binding.layoutAddleadsubmit.shimmerFormsecondStatus,
                    binding.layoutAddleadsubmit.tilFormsecondStatus
                )

                is UIState.Error -> stopShimmerInput(
                    binding.layoutAddleadsubmit.shimmerFormsecondStatus,
                    binding.layoutAddleadsubmit.tilFormsecondStatus
                )


                is UIState.Success -> {
                    viewModel.statuses = state.data
                    stopShimmerInput(
                        binding.layoutAddleadsubmit.shimmerFormsecondStatus,
                        binding.layoutAddleadsubmit.tilFormsecondStatus
                    )
                    (binding.layoutAddleadsubmit.tvFormsecondStatus as MaterialAutoCompleteTextView)
                        .setSimpleItems(state.data.map { it.name }.toTypedArray())
                }
            }
        }

        viewModel.resultProbabilities.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> startShimmerInput(
                    binding.layoutAddleadsubmit.shimmerFormsecondProbability,
                    binding.layoutAddleadsubmit.tilFormsecondProbability
                )

                is UIState.Error -> stopShimmerInput(
                    binding.layoutAddleadsubmit.shimmerFormsecondProbability,
                    binding.layoutAddleadsubmit.tilFormsecondProbability
                )


                is UIState.Success -> {
                    viewModel.probabilities = state.data
                    stopShimmerInput(
                        binding.layoutAddleadsubmit.shimmerFormsecondProbability,
                        binding.layoutAddleadsubmit.tilFormsecondProbability
                    )
                    (binding.layoutAddleadsubmit.tvFormsecondProbability as MaterialAutoCompleteTextView)
                        .setSimpleItems(state.data.map { it.name }.toTypedArray())
                }
            }
        }
        viewModel.resultPostLead.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> {
                    binding.layoutAddleadsubmit.btnFormsecondSubmit.visibility = View.INVISIBLE
                    binding.layoutAddleadsubmit.pbFormsecond.visibility = View.VISIBLE
                }

                is UIState.Error -> {
                    Snackbar.make(
                        requireView(),
                        state.error.message.toString(),
                        Snackbar.LENGTH_LONG
                    ).show()
                    binding.layoutAddleadsubmit.btnFormsecondSubmit.visibility = View.VISIBLE
                    binding.layoutAddleadsubmit.pbFormsecond.visibility = View.GONE
                }


                is UIState.Success -> {
                    doUpdateStatus(state.data.id, statusId)
                }
            }
        }

        viewModel.resultUpdateStatus.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> {
                    binding.layoutAddleadsubmit.btnFormsecondSubmit.visibility = View.INVISIBLE
                    binding.layoutAddleadsubmit.pbFormsecond.visibility = View.VISIBLE
                }

                is UIState.Error -> {
                    Snackbar.make(
                        requireView(),
                        state.error.message.toString(),
                        Snackbar.LENGTH_LONG
                    ).show()
                    binding.layoutAddleadsubmit.btnFormsecondSubmit.visibility = View.VISIBLE
                    binding.layoutAddleadsubmit.pbFormsecond.visibility = View.GONE
                }


                is UIState.Success -> {
                    binding.layoutAddleadsubmit.btnFormsecondSubmit.visibility = View.VISIBLE
                    binding.layoutAddleadsubmit.pbFormsecond.visibility = View.GONE
                    showDialog()
                }
            }
        }
    }

    private fun doUpdateStatus(id: Int?, statusId: String?) {
        if (id != null && statusId != null) {
            viewModel.patchUpdateStatus(
                id,
                UpdateStatusRequest(statusId)
            )
        }
    }

    private fun submit() {
        val branchOfficePart: MultipartBody.Part? = stringToMultipart(
            viewModel.form?.branchOfficeId,
            "branchOfficeId"
        )
        val fullNamePart: MultipartBody.Part? = stringToMultipart(
            viewModel.form?.fullName,
            "fullName"
        )
        val emailPart: MultipartBody.Part? = stringToMultipart(
            viewModel.form?.email,
            "email"
        )
        val phonePart: MultipartBody.Part? = stringToMultipart(
            viewModel.form?.phone,
            "phone"
        )
        val addressPart: MultipartBody.Part? = stringToMultipart(
            viewModel.form?.address,
            "address"
        )
        val latitudePart: MultipartBody.Part? = stringToMultipart(
            viewModel.form?.latitude,
            "latitude"
        )
        val longitudePart: MultipartBody.Part? = stringToMultipart(
            viewModel.form?.longitude,
            "longitude"
        )
        val idNumberPart: MultipartBody.Part? = stringToMultipart(
            viewModel.form?.idNumber,
            "IDNumber"
        )
        val genderPart: MultipartBody.Part? = stringToMultipart(
            viewModel.form?.gender,
            "gender"
        )

        val companyNamePart: MultipartBody.Part? = stringToMultipart(
            viewModel.form?.companyName,
            "companyName"
        )

        val imagePart = FormValidation.imageToMultiPart(
            viewModel.form?.imageUrl,
            "IDNumberPhoto",
            requireContext()
        )


        val typePart: MultipartBody.Part? = dropDownInputToMultipart(
            binding.layoutAddleadsubmit.tvFormsecondType,
            binding.layoutAddleadsubmit.tvFormsecondTypeTitle,
            viewModel.types,
            "typeId",
            false
        )

        val channelPart: MultipartBody.Part? = dropDownInputToMultipart(
            binding.layoutAddleadsubmit.tvFormsecondChannel,
            binding.layoutAddleadsubmit.tvFormsecondChannelTitle,
            viewModel.channels,
            "channelId",
            false
        )

        val mediaPart: MultipartBody.Part? = dropDownInputToMultipart(
            binding.layoutAddleadsubmit.tvFormsecondMedia,
            binding.layoutAddleadsubmit.tvFormsecondMediaTitle,
            viewModel.medias,
            "mediaId",
            false
        )

        val sourcePart: MultipartBody.Part? = dropDownInputToMultipart(
            binding.layoutAddleadsubmit.tvFormsecondSource,
            binding.layoutAddleadsubmit.tvFormsecondSourceTitle,
            viewModel.sources,
            "sourceId",
            false
        )

        val probabilityPart: MultipartBody.Part? = dropDownInputToMultipart(
            binding.layoutAddleadsubmit.tvFormsecondProbability,
            binding.layoutAddleadsubmit.tvFormsecondProbabilityTitle,
            viewModel.probabilities,
            "probabilityId",
            false
        )

        val notePart: MultipartBody.Part? = textInputToMultipart(
            binding.layoutAddleadsubmit.tietFormsecondNote,
            binding.layoutAddleadsubmit.tvFormsecondNoteTitle,
            "generalNotes",
            false
        )
        statusId = dropDownInputToString(
            binding.layoutAddleadsubmit.tvFormsecondStatus,
            viewModel.statuses,
            binding.layoutAddleadsubmit.tvFormsecondStatusTitle,
            false
        )

        if (fullNamePart != null && phonePart != null && latitudePart != null && longitudePart != null && companyNamePart != null && typePart != null && channelPart != null && mediaPart != null && probabilityPart != null && !statusId.isNullOrBlank() && sourcePart != null && notePart != null) {
            if (viewModel.lead == null) {
                viewModel.postLead(
                    PostLeadRequest(
                        fullName = fullNamePart,
                        email = emailPart,
                        phone = phonePart,
                        address = addressPart,
                        latitude = latitudePart,
                        longitude = longitudePart,
                        companyName = companyNamePart,
                        generalNotes = notePart,
                        gender = genderPart,
                        idNumber = idNumberPart,
                        idNumberPhoto = imagePart,
                        branchOfficeId = branchOfficePart,
                        typeId = typePart,
                        channelId = channelPart,
                        mediaId = mediaPart,
                        sourceId = sourcePart,
                        statusId = statusId!!,
                        probabilityId = probabilityPart
                    )
                )
            } else {
                viewModel.postUpdateLead(
                    viewModel.lead!!.id.toString(), PostLeadRequest(
                        fullName = fullNamePart,
                        email = emailPart,
                        phone = phonePart,
                        address = addressPart,
                        latitude = latitudePart,
                        longitude = longitudePart,
                        companyName = companyNamePart,
                        generalNotes = notePart,
                        gender = genderPart,
                        idNumber = idNumberPart,
                        idNumberPhoto = imagePart,
                        branchOfficeId = branchOfficePart,
                        typeId = typePart,
                        channelId = channelPart,
                        mediaId = mediaPart,
                        sourceId = sourcePart,
                        statusId = statusId!!,
                        probabilityId = probabilityPart
                    )
                )
            }
        }
    }

    private fun showDialog() {
        val dialogBinding = LayoutDialogSubmitBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .create()
        dialogBinding.btnDialogSubmit.setOnClickListener {
            dialog.dismiss()
            findNavController().navigate(R.id.action_addLeadSubmitFragment_to_dashboardFragment)
        }
        dialog.show()
    }
}