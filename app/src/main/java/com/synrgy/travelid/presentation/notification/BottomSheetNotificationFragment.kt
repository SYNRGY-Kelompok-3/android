package com.synrgy.travelid.presentation.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.synrgy.travelid.R
import com.synrgy.travelid.databinding.FragmentBottomSheetNotificationBinding
import com.synrgy.travelid.presentation.auth.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetNotificationFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentBottomSheetNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener { goToLoginActivity() }
        binding.btnLater.setOnClickListener { goToHomeFragment() }
    }

    private fun goToHomeFragment() {
        findNavController().navigate(R.id.action_notificationFragment_to_homeFragment)
    }

    private fun goToLoginActivity() {
        LoginActivity.startActivity(requireContext())
    }



}