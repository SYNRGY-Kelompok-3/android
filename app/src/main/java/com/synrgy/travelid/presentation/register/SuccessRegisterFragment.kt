package com.synrgy.travelid.presentation.register

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.synrgy.travelid.R
import com.synrgy.travelid.databinding.FragmentSuccessRegisterBinding

class SuccessRegisterFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSuccessRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSuccessRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSelesai.setOnClickListener {
//            val intentActivity = Intent(activity, OtpActivity::class.java)
//            startActivity(intentActivity)
        }
    }
}