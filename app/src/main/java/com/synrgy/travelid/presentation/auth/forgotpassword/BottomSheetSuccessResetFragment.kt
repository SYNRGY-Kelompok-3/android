package com.synrgy.travelid.presentation.auth.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.synrgy.travelid.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetSuccessResetFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet_success_reset, container, false)
        //!!add arah ke login activity
    }
}