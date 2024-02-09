package com.synrgy.travelid.presentation

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Fade
import android.view.View
import android.view.WindowManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.synrgy.travelid.R
import com.synrgy.travelid.databinding.ActivityMainBinding
import com.synrgy.travelid.presentation.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setStatusBarTransparent()
        setBadgeMenu()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNav.setOnItemSelectedListener { itemId ->
            navigateToDestination(itemId, navController)
            true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val destinationId = destination.id
            binding.bottomNav.setItemSelected(destinationId)

            when (destinationId) {
                R.id.editProfileFragment -> {
                    hideBottomNav()
                }
                R.id.orderHistoryFragment -> {
                    hideBottomNav()
                }
                R.id.detailOrderHistoryFragment -> {
                    hideBottomNav()
                }
                R.id.searchFragment -> {
                    hideBottomNav()
                }
                else -> {
                    showBottomNav()
                }
            }
        }
    }

    private fun setBadgeMenu() {
        binding.bottomNav.showBadge(R.id.notificationFragment)
    }

    private fun setStatusBarTransparent() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        window.statusBarColor = Color.TRANSPARENT
    }

    private fun navigateToDestination(itemId: Int, navController: NavController) {
        navController.navigate(itemId)
    }

    private fun hideBottomNav() {
        binding.bottomNav.visibility = View.GONE
    }

    private fun showBottomNav() {
        binding.bottomNav.visibility = View.VISIBLE
    }
}

