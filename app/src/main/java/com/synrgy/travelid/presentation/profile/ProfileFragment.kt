package com.synrgy.travelid.presentation.profile

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.avatarfirst.avatargenlib.AvatarGenerator
import com.bumptech.glide.Glide
import com.synrgy.travelid.R
import com.synrgy.travelid.databinding.FragmentProfileBinding
import com.synrgy.travelid.domain.model.main.UserProfile
import com.synrgy.travelid.presentation.auth.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userProfile()
        viewModel.checkLoggedIn()
        bindView()
        observeViewModel()
    }

    private fun bindView() {
        binding.btnMasuk.setOnClickListener { LoginActivity.startActivity(requireContext()) }
        binding.btnKeluar.setOnClickListener { viewModel.clearDataUser() }
        binding.container1.setOnClickListener { goToEditProfile() }
    }

    private fun observeViewModel() {
        viewModel.loggedIn.observe(viewLifecycleOwner, ::handleLoggedIn)
        viewModel.notLoggedIn.observe(viewLifecycleOwner, ::handleNotLoggedIn)
        viewModel.openLoginPage.observe(viewLifecycleOwner, ::handleOpenLoginPage)
    }

    private fun handleLoggedIn(isLoggedIn: Boolean) {
        if (isLoggedIn) {
            binding.logo.visibility = View.GONE
            binding.cardLogin.visibility = View.GONE

            binding.profileUser.visibility = View.VISIBLE
            binding.cardUserMenu.visibility = View.VISIBLE
            binding.cardFulfillProfile.visibility = View.VISIBLE
            binding.cardHelp.visibility = View.VISIBLE
            binding.btnKeluar.visibility = View.VISIBLE
            binding.tvVersion.visibility = View.VISIBLE
            viewModel.showUser.observe(viewLifecycleOwner, ::handleShowUserProfile)
        }
    }

    private fun handleNotLoggedIn(isNotLoggedIn: Boolean) {
        if (isNotLoggedIn) {
            binding.logo.visibility = View.VISIBLE
            binding.cardLogin.visibility = View.VISIBLE
            binding.cardHelp.visibility = View.VISIBLE
            binding.tvVersion.visibility = View.VISIBLE

            binding.profileUser.visibility = View.GONE
            binding.cardUserMenu.visibility = View.GONE
            binding.cardFulfillProfile.visibility = View.GONE
            binding.btnKeluar.visibility = View.GONE
        }
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

        binding.tvUsername.text = userProfile.name
        binding.tvUserEmail.text = userProfile.email
    }

    private fun goToEditProfile() {
        findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
    }

    private fun handleOpenLoginPage(isLoggedOut: Boolean) {
        if (isLoggedOut) {
            LoginActivity.startActivity(requireContext())
        }
    }
}
