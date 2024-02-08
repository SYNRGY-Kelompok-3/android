package com.synrgy.travelid.presentation.layanan

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.synrgy.travelid.R

class KetentuanLayananFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ketentuan_layanan, container, false)

        val textView = view.findViewById<TextView>(R.id.tv_priv_title_1)
        val fullTitle = textView.text.toString()

        val startIndex = fullTitle.indexOf("Travel Indonesia")
        val spanString = SpannableString(fullTitle)

        val blue = ContextCompat.getColor(requireContext(), R.color.primary_blue)
        val blueSpan = ForegroundColorSpan(blue)

        spanString.setSpan(blueSpan, startIndex, startIndex + "Travel Indonesia".length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.text = spanString

        return view
    }


}