package com.synrgy.travelid.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialCalendar
import com.google.android.material.datepicker.MaterialDatePicker
import com.synrgy.travelid.R
import com.synrgy.travelid.databinding.FragmentPilihTanggalBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class PilihTanggalFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentPilihTanggalBinding
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPilihTanggalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSimpan.setOnClickListener {
            val selectedDate = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID")).format(calendar.time)
            parentFragmentManager.setFragmentResult("dateRequestKey", bundleOf("selectedDate" to selectedDate))
            dismiss()
        }

        binding.datePicker.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
        }
    }
}
