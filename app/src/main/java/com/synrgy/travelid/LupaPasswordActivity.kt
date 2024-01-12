package com.synrgy.travelid

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import com.synrgy.travelid.BottomSheetFragment
import com.synrgy.travelid.LupaPasswordViewModel
import com.synrgy.travelid.domain.model.auth.ResetPasswordRequest
import com.synrgy.travelid.databinding.ActivityLupaPasswordBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LupaPasswordActivity : AppCompatActivity() {

    companion object {
        fun provideIntent(context: Context): Intent {
            return Intent(context, LupaPasswordActivity::class.java)
        }
        fun startActivity(context: Context) {
            context.startActivity(provideIntent(context))
        }
    }

    private var binding: ActivityLupaPasswordBinding? = null
    private val viewModel by viewModel<LupaPasswordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityLupaPasswordBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        observeViewModel()
    }

    private fun observeViewModel(){
        binding?.buttonKirimLupaPw?.setOnClickListener { handleValidation() }
        viewModel.error.observe(this, ::handleError)
    }

    private fun handleValidation() {
        val email = binding?.etInputEmail?.text?.toString().orEmpty()
        val request = ResetPasswordRequest(email)
        viewModel.resetPassword(request)

        val bottomSheetFragment = BottomSheetFragment()
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }

    private fun handleError(error: String?){
        binding?.etInputEmail?.error = error
    }
}