package com.synrgy.travelid.presentation.auth.forgotpassword

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.synrgy.travelid.R
import com.synrgy.travelid.databinding.FragmentBottomSheetSuccessResetBinding
import com.synrgy.travelid.presentation.auth.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetSuccessResetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetSuccessResetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetSuccessResetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonCheckEmail.setOnClickListener {
            val intentActivity = Intent(activity, LoginActivity::class.java)
            startActivity(intentActivity)
        }
    }
}