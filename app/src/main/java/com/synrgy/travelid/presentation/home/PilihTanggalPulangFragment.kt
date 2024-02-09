package com.synrgy.travelid.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.synrgy.travelid.R
import com.synrgy.travelid.databinding.FragmentPilihTanggalBinding
import com.synrgy.travelid.databinding.FragmentPilihTanggalPulangBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class PilihTanggalPulangFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentPilihTanggalPulangBinding
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPilihTanggalPulangBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSimpan.setOnClickListener {
            val selectedDate = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID")).format(calendar.time)
            parentFragmentManager.setFragmentResult("dateRequestKeyPulang", bundleOf("selectedDatePulang" to selectedDate))
            dismiss()
        }

        binding.datePicker.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
        }
    }
}
