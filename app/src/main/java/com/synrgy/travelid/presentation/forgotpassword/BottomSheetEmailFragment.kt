package com.synrgy.travelid.presentation.forgotpassword

import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.synrgy.travelid.R
import com.synrgy.travelid.databinding.FragmentBottomSheetEmailBinding
import com.synrgy.travelid.presentation.forgotpassword.LupaPasswordActivity.Companion.USER_EMAIL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetEmailFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetEmailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val themedInflater = inflater.cloneInContext(ContextThemeWrapper(requireContext(), R.style.AppBottomSheetDialogTheme))
        binding = FragmentBottomSheetEmailBinding.inflate(themedInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindView()
    }

    private fun bindView(){
        binding.buttonCheckEmail.setOnClickListener {
            handleOpenValidateOTPActivity()
        }
    }

    private fun handleOpenValidateOTPActivity() {
        val userEmail = activity?.intent?.getStringExtra(USER_EMAIL)
        val intentActivity = Intent(activity, ValidateOTPActivity::class.java)
        intentActivity.putExtra(USER_EMAIL, userEmail)
        startActivity(intentActivity)
    }
}
