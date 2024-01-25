package com.synrgy.travelid.presentation.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.synrgy.travelid.R
import com.synrgy.travelid.databinding.ActivityRoutingBinding
import com.synrgy.travelid.presentation.MainActivity
import com.synrgy.travelid.presentation.auth.register.RegisterActivity
import com.synrgy.travelid.presentation.onboarding.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoutingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoutingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityRoutingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashScreen.setOnExitAnimationListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}