package com.synrgy.travelid.presentation.home.booking.invoice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.synrgy.travelid.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InvoiceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invoice, container, false)
    }
}