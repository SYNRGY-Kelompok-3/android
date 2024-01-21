package com.synrgy.travelid.presentation.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.synrgy.travelid.databinding.FragmentConfirmBinding
import com.synrgy.travelid.presentation.forgotpassword.LupaPasswordActivity
import com.synrgy.travelid.presentation.forgotpassword.ValidateOTPActivity
import com.synrgy.travelid.presentation.register.RegisterActivity.Companion.USER_EMAIL_REGISTER

class ConfirmFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentConfirmBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonCheckEmail.setOnClickListener {
            val userEmail = activity?.intent?.getStringExtra(USER_EMAIL_REGISTER)
            val intentActivity = Intent(activity, OtpActivity::class.java)
            intentActivity.putExtra(USER_EMAIL_REGISTER, userEmail)
            startActivity(intentActivity)
        }
    }
}