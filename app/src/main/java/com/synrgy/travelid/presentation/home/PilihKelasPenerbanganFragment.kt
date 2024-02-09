package com.synrgy.travelid.presentation.home

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.synrgy.travelid.R
import com.synrgy.travelid.databinding.FragmentPilihKelasPenerbanganBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PilihKelasPenerbanganFragment : BottomSheetDialogFragment() {
    interface KelasPenerbanganSelectionListener {
        fun onKelasPenerbanganSelected(kelas: String)
    }
    private var kelasPenerbanganSelectionListener: KelasPenerbanganSelectionListener? = null
    private lateinit var binding: FragmentPilihKelasPenerbanganBinding
    private var selectedClass: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPilihKelasPenerbanganBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    private fun bindView() {
        binding.btnSimpan.setOnClickListener {
            selectedClass?.let { kelas ->
                kelasPenerbanganSelectionListener?.onKelasPenerbanganSelected(kelas)
                dismiss()
            }
        }

        binding.containerEkonomi.setOnClickListener {
            val colorPrimary = ContextCompat.getColor(requireContext(), R.color.primary)
            val colorTransparent = ContextCompat.getColor(requireContext(), R.color.primary_transparent)
            binding.containerEkonomi.setStrokeColor(ColorStateList.valueOf(colorPrimary))
            binding.containerEkonomi.setCardBackgroundColor(colorTransparent)
            binding.tvEkonomi.setTextColor(colorPrimary)
            selectClass("Ekonomi")

            val colorWhite = ContextCompat.getColor(requireContext(), R.color.white)
            val colorStroke = ContextCompat.getColor(requireContext(), R.color.stroke)
            val colorBlack = ContextCompat.getColor(requireContext(), R.color.black)
            binding.containerBisnis.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerBisnis.setCardBackgroundColor(colorWhite)
            binding.tvBisnis.setTextColor(colorBlack)
        }

        binding.containerBisnis.setOnClickListener {
            val colorPrimary = ContextCompat.getColor(requireContext(), R.color.primary)
            val colorTransparent = ContextCompat.getColor(requireContext(), R.color.primary_transparent)
            binding.containerBisnis.setStrokeColor(ColorStateList.valueOf(colorPrimary))
            binding.containerBisnis.setCardBackgroundColor(colorTransparent)
            binding.tvBisnis.setTextColor(colorPrimary)
            selectClass("Bisnis")

            val colorWhite = ContextCompat.getColor(requireContext(), R.color.white)
            val colorStroke = ContextCompat.getColor(requireContext(), R.color.stroke)
            val colorBlack = ContextCompat.getColor(requireContext(), R.color.black)
            binding.containerEkonomi.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerEkonomi.setCardBackgroundColor(colorWhite)
            binding.tvEkonomi.setTextColor(colorBlack)
        }
    }

    private fun selectClass(kelas: String) {
        selectedClass = kelas
    }

    fun setKelasPenerbanganSelectionListener(listener: KelasPenerbanganSelectionListener) {
        kelasPenerbanganSelectionListener = listener
    }
}