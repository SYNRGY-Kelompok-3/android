package com.synrgy.travelid.presentation.profile.editprofile

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.avatarfirst.avatargenlib.AvatarGenerator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.github.dhaval2404.imagepicker.ImagePicker
import com.synrgy.travelid.R
import com.synrgy.travelid.common.uriToFile
import com.synrgy.travelid.databinding.FragmentEditProfileBinding
import com.synrgy.travelid.domain.model.main.EditProfileRequest
import com.synrgy.travelid.domain.model.main.UserProfile
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel: EditProfileViewModel by viewModels()
    private var id: Int = 0
    private var uri: String = ""
    private var fileImage: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userProfile()
        bindView()
        observeViewModel()
        setStatusBarColor()
    }

    private fun bindView() {
        binding.icBack.setOnClickListener { findNavController().popBackStack() }
        binding.btnEditProfile.setOnClickListener { handleEditProfile() }
        binding.tvEditProfilePicture.setOnClickListener { openImagePicker() }
        binding.etTanggalLahir.isFocusableInTouchMode = false
        binding.etJenisKelamin.isFocusableInTouchMode = false
    }

    private fun observeViewModel() {
        viewModel.showUser.observe(viewLifecycleOwner, ::handleShowUserProfile)
    }

    private fun handleShowUserProfile(userProfile: UserProfile) {
        Glide.with(requireContext())
            .load("https://travelid-backend-java-dev.up.railway.app/showFile/${userProfile.profilePicture}")
            .placeholder(
                AvatarGenerator.AvatarBuilder(requireContext())
                    .setTextSize(28)
                    .setAvatarSize(150)
                    .toSquare()
                    .setLabel(userProfile.name)
                    .build()
            )
            .into(binding.ivUserProfile)

        binding.etNama.setText(userProfile.name)
        binding.etNomorHandphone.setText(userProfile.phoneNumber)
        binding.etEmail.setText(userProfile.email)
        binding.etTanggalLahir.setText(userProfile.dateOfBirth)
        binding.etJenisKelamin.setText(userProfile.gender)
        id = userProfile.id
    }

    private fun handleEditProfile() {
        val nama = binding.etNama.text.toString()
        val nomorHandphone = binding.etNomorHandphone.text.toString()
        val tanggalLahir = binding.etTanggalLahir.text.toString()
        val jenisKelamin = binding.etJenisKelamin.text.toString()

        viewModel.editProfilePicture(fileImage, id)
        val request = EditProfileRequest(id, nama, jenisKelamin, tanggalLahir, nomorHandphone)
        viewModel.editProfile(request)

        findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
    }

    private fun openImagePicker() {
        ImagePicker.with(this)
            .crop()
            .saveDir(
                File(
                    requireContext().externalCacheDir,
                    "ImagePicker"
                )
            )
            .compress(1024)
            .maxResultSize(
                1080,
                1080
            )
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data
                    uri = fileUri.toString()
                    if (fileUri != null) {
                        fileImage = uriToFile(fileUri, requireContext())
                        loadImage(fileUri)
                    }
                }

                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    private fun loadImage(uri: Uri) {
        binding.apply {
            Glide.with(binding.root)
                .load(uri)
                .transform(CenterCrop(), RoundedCorners(12))
                .into(ivUserProfile)
        }
    }

    private fun setStatusBarColor() {
        requireActivity().window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}