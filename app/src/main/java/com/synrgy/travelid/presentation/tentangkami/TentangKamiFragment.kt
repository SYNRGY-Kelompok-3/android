package com.synrgy.travelid.presentation.tentangkami

import android.graphics.Color
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.synrgy.travelid.R
import android.text.Spannable
import android.text.SpannableString
import android.widget.TextView
import androidx.core.content.ContextCompat

class TentangKamiFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tentang_kami, container, false)

        val textView = view.findViewById<TextView>(R.id.tv_tk_title_1)
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