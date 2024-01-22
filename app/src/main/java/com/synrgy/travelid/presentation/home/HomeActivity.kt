package com.synrgy.travelid.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.synrgy.travelid.R
import com.synrgy.travelid.databinding.ActivityHomeBinding
import com.synrgy.travelid.presentation.auth.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindView()
        observeViewModel()
    }

    private fun bindView() {
        binding.btnLogout.setOnClickListener { viewModel.clearDataUser() }
    }

    private fun observeViewModel() {
        viewModel.openLoginPage.observe(this@HomeActivity, ::handleOpenLoginPage)
    }

    private fun handleOpenLoginPage(isLoggedOut: Boolean) {
        if(isLoggedOut){
            LoginActivity.startActivity(this)
        }
    }
}