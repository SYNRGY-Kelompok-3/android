package com.synrgy.travelid

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.synrgy.travelid.R

class BottomSheetFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
        val buttonCekEmail = view?.findViewById<Button>(R.id.button_check_email)
        buttonCekEmail?.setOnClickListener{
            //seharusnya pindah ke OTPActivity nanti
            val intent = Intent(activity, AturUlangPasswordActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}