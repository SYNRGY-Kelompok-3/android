package com.synrgy.travelid.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.synrgy.travelid.R
import com.synrgy.travelid.domain.model.main.Travel

class SearchActivity : AppCompatActivity() {
    private lateinit var dateRecyclerView: RecyclerView
    private lateinit var flightRecyclerView: RecyclerView
    private lateinit var dateAdapter: SearchAdapter
    private lateinit var flightAdapter: SearchAdapter
    private val dateList = listOf(
        Pair("1 Januari", "Rp. 100.000"),
        Pair("2 Januari", "Rp. 150.000"),
        Pair("3 Januari", "Rp. 120.000"),
        Pair("4 Januari", "Rp. 130.000"),
        Pair("4 Januari", "Rp. 130.000"),
        Pair("4 Januari", "Rp. 130.000"),
        Pair("4 Januari", "Rp. 130.000"),
        Pair("4 Januari", "Rp. 130.000")
    )
    private val flightList = listOf(
        Travel(
            "Garuda Indonesia",
            "Rp. 1.500.000/pax",
            "Senin",
            "Senin",
            "07:00",
            "09:30",
            "YIA",
            "CGK",
            "2 Jam 30 Menit"
        ),
        Travel(
            "Garuda Indonesia",
            "Rp. 1.500.000/pax",
            "Senin",
            "Senin",
            "07:00",
            "09:30",
            "YIA",
            "CGK",
            "2 Jam 30 Menit"
        ),
        Travel(
            "Garuda Indonesia",
            "Rp. 1.500.000/pax",
            "Senin",
            "Senin",
            "07:00",
            "09:30",
            "YIA",
            "CGK",
            "2 Jam 30 Menit"
        ),
        Travel(
            "Garuda Indonesia",
            "Rp. 1.500.000/pax",
            "Senin",
            "Senin",
            "07:00",
            "09:30",
            "YIA",
            "CGK",
            "2 Jam 30 Menit"
        ),
        Travel(
            "Garuda Indonesia",
            "Rp. 1.500.000/pax",
            "Senin",
            "Senin",
            "07:00",
            "09:30",
            "YIA",
            "CGK",
            "2 Jam 30 Menit"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        dateRecyclerView = findViewById(R.id.rv_list_date)
        dateAdapter = SearchAdapter(dateList, emptyList())
        dateRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        dateRecyclerView.adapter = dateAdapter

        flightRecyclerView = findViewById(R.id.rv_list_travel)
        flightAdapter = SearchAdapter(emptyList(), flightList)
        flightRecyclerView.layoutManager = LinearLayoutManager(this)
        flightRecyclerView.adapter = flightAdapter
    }
}
