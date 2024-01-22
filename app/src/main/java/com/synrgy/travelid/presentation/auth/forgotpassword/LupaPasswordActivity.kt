package com.synrgy.travelid.presentation.auth.forgotpassword

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import com.synrgy.travelid.domain.model.ResetPasswordRequest
import com.synrgy.travelid.databinding.ActivityLupaPasswordBinding
import com.synrgy.travelid.domain.model.ResetPassword
import com.synrgy.travelid.presentation.auth.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LupaPasswordActivity : AppCompatActivity() {

    companion object {
        fun provideIntent(context: Context): Intent {
            return Intent(context, LupaPasswordActivity::class.java)
        }
        fun startActivity(context: Context) {
            context.startActivity(provideIntent(context))
        }

        const val USER_EMAIL = "UserEmail"
    }

    private lateinit var binding: ActivityLupaPasswordBinding
    private val viewModel: LupaPasswordViewModel by viewModels()
    private var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityLupaPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindView()
        observeViewModel()
    }

    private fun bindView(){
        binding.buttonKirimLupaPw.setOnClickListener { handleValidation() }
        binding.icBack.setOnClickListener { handleGoBack() }
        binding.etInputEmail.requestFocus()
        handleEditTextOnChanged()
    }

    private fun observeViewModel(){
        viewModel.resetPasswordResponse.observe(this, ::handleResponse)
    }

    private fun handleValidation() {
        email = binding.etInputEmail.text.toString()

        if(email.isEmpty()){
            binding.tilInputEmail.error = "Email ga boleh kosong ya!"
        }else{
            val request = ResetPasswordRequest(email)
            viewModel.resetPassword(request)
        }
    }

    private fun handleResponse(response: ResetPassword){
        if(response.message == "sukses"){ handleOpenBottomSheetDialog() }
        else if(response.message == "Email not found"){
            binding.tilInputEmail.error = "Email belum terdaftar nih!"
        }
    }

    private fun handleOpenBottomSheetDialog() {
        intent.putExtra(USER_EMAIL, email)
        val bottomSheetFragment = BottomSheetEmailFragment()
        bottomSheetFragment.isCancelable = false
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }

    private fun handleGoBack() {
        startActivity(Intent(this@LupaPasswordActivity, LoginActivity::class.java))
    }

    private fun handleEditTextOnChanged() {
        binding.etInputEmail.doOnTextChanged { text, _, _, _ ->
            if(text!!.isEmpty()){
                binding.tilInputEmail.error = "Email ga boleh kosong ya!"
            }else if(text.isNotEmpty()){
                binding.tilInputEmail.error = null
            }
        }
    }
}