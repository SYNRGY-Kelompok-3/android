package com.synrgy.travelid.presentation.home

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.synrgy.travelid.R
import com.synrgy.travelid.databinding.FragmentPilihKotaAwalBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PilihKotaAwalFragment : BottomSheetDialogFragment() {
    interface OriginCitySelectionListener {
        fun onOriginCitySelected(city: String)
    }
    private var originCitySelectionListener: OriginCitySelectionListener? = null
    private lateinit var binding: FragmentPilihKotaAwalBinding
    private var selectedCity: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPilihKotaAwalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    private fun bindView() {
        binding.btnSimpan.setOnClickListener {
            selectedCity?.let { city ->
                originCitySelectionListener?.onOriginCitySelected(city)
                dismiss()
            }
        }

        binding.containerJakarta.setOnClickListener {
            val colorPrimary = ContextCompat.getColor(requireContext(), R.color.primary)
            val colorTransparent = ContextCompat.getColor(requireContext(), R.color.primary_transparent)
            binding.containerJakarta.setStrokeColor(ColorStateList.valueOf(colorPrimary))
            binding.containerJakarta.setCardBackgroundColor(colorTransparent)
            binding.tvJakarta.setTextColor(colorPrimary)
            selectCity("Jakarta")

            val colorWhite = ContextCompat.getColor(requireContext(), R.color.white)
            val colorStroke = ContextCompat.getColor(requireContext(), R.color.stroke)
            val colorBlack = ContextCompat.getColor(requireContext(), R.color.black)
            binding.containerSurabaya.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerSurabaya.setCardBackgroundColor(colorWhite)
            binding.tvSurabaya.setTextColor(colorBlack)

            binding.containerBali.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerBali.setCardBackgroundColor(colorWhite)
            binding.tvBali.setTextColor(colorBlack)

            binding.containerBandung.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerBandung.setCardBackgroundColor(colorWhite)
            binding.tvBandung.setTextColor(colorBlack)

            binding.containerYogyakarta.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerYogyakarta.setCardBackgroundColor(colorWhite)
            binding.tvYogyakarta.setTextColor(colorBlack)

            binding.containerPadang.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerPadang.setCardBackgroundColor(colorWhite)
            binding.tvPadang.setTextColor(colorBlack)

            binding.containerPalembang.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerPalembang.setCardBackgroundColor(colorWhite)
            binding.tvPalembang.setTextColor(colorBlack)

            binding.containerPangkalPinang.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerPangkalPinang.setCardBackgroundColor(colorWhite)
            binding.tvPangkalPinang.setTextColor(colorBlack)
        }

        binding.containerSurabaya.setOnClickListener {
            val colorPrimary = ContextCompat.getColor(requireContext(), R.color.primary)
            val colorTransparent = ContextCompat.getColor(requireContext(), R.color.primary_transparent)
            binding.containerSurabaya.setStrokeColor(ColorStateList.valueOf(colorPrimary))
            binding.containerSurabaya.setCardBackgroundColor(colorTransparent)
            binding.tvSurabaya.setTextColor(colorPrimary)
            selectCity("Surabaya")

            val colorWhite = ContextCompat.getColor(requireContext(), R.color.white)
            val colorStroke = ContextCompat.getColor(requireContext(), R.color.stroke)
            val colorBlack = ContextCompat.getColor(requireContext(), R.color.black)
            binding.containerJakarta.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerJakarta.setCardBackgroundColor(colorWhite)
            binding.tvJakarta.setTextColor(colorBlack)

            binding.containerBali.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerBali.setCardBackgroundColor(colorWhite)
            binding.tvBali.setTextColor(colorBlack)

            binding.containerBandung.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerBandung.setCardBackgroundColor(colorWhite)
            binding.tvBandung.setTextColor(colorBlack)

            binding.containerYogyakarta.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerYogyakarta.setCardBackgroundColor(colorWhite)
            binding.tvYogyakarta.setTextColor(colorBlack)

            binding.containerPadang.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerPadang.setCardBackgroundColor(colorWhite)
            binding.tvPadang.setTextColor(colorBlack)

            binding.containerPalembang.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerPalembang.setCardBackgroundColor(colorWhite)
            binding.tvPalembang.setTextColor(colorBlack)

            binding.containerPangkalPinang.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerPangkalPinang.setCardBackgroundColor(colorWhite)
            binding.tvPangkalPinang.setTextColor(colorBlack)
        }

        binding.containerBali.setOnClickListener {
            val colorPrimary = ContextCompat.getColor(requireContext(), R.color.primary)
            val colorTransparent = ContextCompat.getColor(requireContext(), R.color.primary_transparent)
            binding.containerBali.setStrokeColor(ColorStateList.valueOf(colorPrimary))
            binding.containerBali.setCardBackgroundColor(colorTransparent)
            binding.tvBali.setTextColor(colorPrimary)
            selectCity("Bali")

            val colorWhite = ContextCompat.getColor(requireContext(), R.color.white)
            val colorStroke = ContextCompat.getColor(requireContext(), R.color.stroke)
            val colorBlack = ContextCompat.getColor(requireContext(), R.color.black)
            binding.containerJakarta.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerJakarta.setCardBackgroundColor(colorWhite)
            binding.tvJakarta.setTextColor(colorBlack)

            binding.containerSurabaya.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerSurabaya.setCardBackgroundColor(colorWhite)
            binding.tvSurabaya.setTextColor(colorBlack)

            binding.containerBandung.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerBandung.setCardBackgroundColor(colorWhite)
            binding.tvBandung.setTextColor(colorBlack)

            binding.containerYogyakarta.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerYogyakarta.setCardBackgroundColor(colorWhite)
            binding.tvYogyakarta.setTextColor(colorBlack)

            binding.containerPadang.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerPadang.setCardBackgroundColor(colorWhite)
            binding.tvPadang.setTextColor(colorBlack)

            binding.containerPalembang.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerPalembang.setCardBackgroundColor(colorWhite)
            binding.tvPalembang.setTextColor(colorBlack)

            binding.containerPangkalPinang.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerPangkalPinang.setCardBackgroundColor(colorWhite)
            binding.tvPangkalPinang.setTextColor(colorBlack)
        }

        binding.containerBandung.setOnClickListener {
            val colorPrimary = ContextCompat.getColor(requireContext(), R.color.primary)
            val colorTransparent = ContextCompat.getColor(requireContext(), R.color.primary_transparent)
            binding.containerBandung.setStrokeColor(ColorStateList.valueOf(colorPrimary))
            binding.containerBandung.setCardBackgroundColor(colorTransparent)
            binding.tvBandung.setTextColor(colorPrimary)
            selectCity("Bandung")

            val colorWhite = ContextCompat.getColor(requireContext(), R.color.white)
            val colorStroke = ContextCompat.getColor(requireContext(), R.color.stroke)
            val colorBlack = ContextCompat.getColor(requireContext(), R.color.black)
            binding.containerJakarta.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerJakarta.setCardBackgroundColor(colorWhite)
            binding.tvJakarta.setTextColor(colorBlack)

            binding.containerSurabaya.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerSurabaya.setCardBackgroundColor(colorWhite)
            binding.tvSurabaya.setTextColor(colorBlack)

            binding.containerBali.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerBali.setCardBackgroundColor(colorWhite)
            binding.tvBali.setTextColor(colorBlack)

            binding.containerYogyakarta.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerYogyakarta.setCardBackgroundColor(colorWhite)
            binding.tvYogyakarta.setTextColor(colorBlack)

            binding.containerPadang.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerPadang.setCardBackgroundColor(colorWhite)
            binding.tvPadang.setTextColor(colorBlack)

            binding.containerPalembang.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerPalembang.setCardBackgroundColor(colorWhite)
            binding.tvPalembang.setTextColor(colorBlack)

            binding.containerPangkalPinang.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerPangkalPinang.setCardBackgroundColor(colorWhite)
            binding.tvPangkalPinang.setTextColor(colorBlack)
        }

        binding.containerYogyakarta.setOnClickListener {
            val colorPrimary = ContextCompat.getColor(requireContext(), R.color.primary)
            val colorTransparent = ContextCompat.getColor(requireContext(), R.color.primary_transparent)
            binding.containerYogyakarta.setStrokeColor(ColorStateList.valueOf(colorPrimary))
            binding.containerYogyakarta.setCardBackgroundColor(colorTransparent)
            binding.tvYogyakarta.setTextColor(colorPrimary)
            selectCity("Yogyakarta")

            val colorWhite = ContextCompat.getColor(requireContext(), R.color.white)
            val colorStroke = ContextCompat.getColor(requireContext(), R.color.stroke)
            val colorBlack = ContextCompat.getColor(requireContext(), R.color.black)
            binding.containerJakarta.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerJakarta.setCardBackgroundColor(colorWhite)
            binding.tvJakarta.setTextColor(colorBlack)

            binding.containerSurabaya.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerSurabaya.setCardBackgroundColor(colorWhite)
            binding.tvSurabaya.setTextColor(colorBlack)

            binding.containerBali.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerBali.setCardBackgroundColor(colorWhite)
            binding.tvBali.setTextColor(colorBlack)

            binding.containerBandung.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerBandung.setCardBackgroundColor(colorWhite)
            binding.tvBandung.setTextColor(colorBlack)

            binding.containerPadang.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerPadang.setCardBackgroundColor(colorWhite)
            binding.tvPadang.setTextColor(colorBlack)

            binding.containerPalembang.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerPalembang.setCardBackgroundColor(colorWhite)
            binding.tvPalembang.setTextColor(colorBlack)

            binding.containerPangkalPinang.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerPangkalPinang.setCardBackgroundColor(colorWhite)
            binding.tvPangkalPinang.setTextColor(colorBlack)
        }

        binding.containerPadang.setOnClickListener {
            val colorPrimary = ContextCompat.getColor(requireContext(), R.color.primary)
            val colorTransparent = ContextCompat.getColor(requireContext(), R.color.primary_transparent)
            binding.containerPadang.setStrokeColor(ColorStateList.valueOf(colorPrimary))
            binding.containerPadang.setCardBackgroundColor(colorTransparent)
            binding.tvPadang.setTextColor(colorPrimary)
            selectCity("Padang")

            val colorWhite = ContextCompat.getColor(requireContext(), R.color.white)
            val colorStroke = ContextCompat.getColor(requireContext(), R.color.stroke)
            val colorBlack = ContextCompat.getColor(requireContext(), R.color.black)
            binding.containerJakarta.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerJakarta.setCardBackgroundColor(colorWhite)
            binding.tvJakarta.setTextColor(colorBlack)

            binding.containerSurabaya.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerSurabaya.setCardBackgroundColor(colorWhite)
            binding.tvSurabaya.setTextColor(colorBlack)

            binding.containerBali.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerBali.setCardBackgroundColor(colorWhite)
            binding.tvBali.setTextColor(colorBlack)

            binding.containerBandung.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerBandung.setCardBackgroundColor(colorWhite)
            binding.tvBandung.setTextColor(colorBlack)

            binding.containerYogyakarta.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerYogyakarta.setCardBackgroundColor(colorWhite)
            binding.tvYogyakarta.setTextColor(colorBlack)

            binding.containerPalembang.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerPalembang.setCardBackgroundColor(colorWhite)
            binding.tvPalembang.setTextColor(colorBlack)

            binding.containerPangkalPinang.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerPangkalPinang.setCardBackgroundColor(colorWhite)
            binding.tvPangkalPinang.setTextColor(colorBlack)
        }

        binding.containerPalembang.setOnClickListener {
            val colorPrimary = ContextCompat.getColor(requireContext(), R.color.primary)
            val colorTransparent = ContextCompat.getColor(requireContext(), R.color.primary_transparent)
            binding.containerPalembang.setStrokeColor(ColorStateList.valueOf(colorPrimary))
            binding.containerPalembang.setCardBackgroundColor(colorTransparent)
            binding.tvPalembang.setTextColor(colorPrimary)
            selectCity("Palembang")

            val colorWhite = ContextCompat.getColor(requireContext(), R.color.white)
            val colorStroke = ContextCompat.getColor(requireContext(), R.color.stroke)
            val colorBlack = ContextCompat.getColor(requireContext(), R.color.black)
            binding.containerJakarta.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerJakarta.setCardBackgroundColor(colorWhite)
            binding.tvJakarta.setTextColor(colorBlack)

            binding.containerSurabaya.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerSurabaya.setCardBackgroundColor(colorWhite)
            binding.tvSurabaya.setTextColor(colorBlack)

            binding.containerBali.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerBali.setCardBackgroundColor(colorWhite)
            binding.tvBali.setTextColor(colorBlack)

            binding.containerBandung.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerBandung.setCardBackgroundColor(colorWhite)
            binding.tvBandung.setTextColor(colorBlack)

            binding.containerYogyakarta.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerYogyakarta.setCardBackgroundColor(colorWhite)
            binding.tvYogyakarta.setTextColor(colorBlack)

            binding.containerPadang.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerPadang.setCardBackgroundColor(colorWhite)
            binding.tvPadang.setTextColor(colorBlack)

            binding.containerPangkalPinang.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerPangkalPinang.setCardBackgroundColor(colorWhite)
            binding.tvPangkalPinang.setTextColor(colorBlack)
        }

        binding.containerPangkalPinang.setOnClickListener {
            val colorPrimary = ContextCompat.getColor(requireContext(), R.color.primary)
            val colorTransparent = ContextCompat.getColor(requireContext(), R.color.primary_transparent)
            binding.containerPangkalPinang.setStrokeColor(ColorStateList.valueOf(colorPrimary))
            binding.containerPangkalPinang.setCardBackgroundColor(colorTransparent)
            binding.tvPangkalPinang.setTextColor(colorPrimary)
            selectCity("Pangkal Pinang")

            val colorWhite = ContextCompat.getColor(requireContext(), R.color.white)
            val colorStroke = ContextCompat.getColor(requireContext(), R.color.stroke)
            val colorBlack = ContextCompat.getColor(requireContext(), R.color.black)
            binding.containerJakarta.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerJakarta.setCardBackgroundColor(colorWhite)
            binding.tvJakarta.setTextColor(colorBlack)

            binding.containerSurabaya.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerSurabaya.setCardBackgroundColor(colorWhite)
            binding.tvSurabaya.setTextColor(colorBlack)

            binding.containerBali.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerBali.setCardBackgroundColor(colorWhite)
            binding.tvBali.setTextColor(colorBlack)

            binding.containerBandung.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerBandung.setCardBackgroundColor(colorWhite)
            binding.tvBandung.setTextColor(colorBlack)

            binding.containerYogyakarta.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerYogyakarta.setCardBackgroundColor(colorWhite)
            binding.tvYogyakarta.setTextColor(colorBlack)

            binding.containerPadang.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerPadang.setCardBackgroundColor(colorWhite)
            binding.tvPadang.setTextColor(colorBlack)

            binding.containerPalembang.setStrokeColor(ColorStateList.valueOf(colorStroke))
            binding.containerPalembang.setCardBackgroundColor(colorWhite)
            binding.tvPalembang.setTextColor(colorBlack)
        }
    }

    private fun selectCity(cityName: String) {
        selectedCity = cityName
    }

    fun setOriginCitySelectionListener(listener: OriginCitySelectionListener) {
        originCitySelectionListener = listener
    }
}