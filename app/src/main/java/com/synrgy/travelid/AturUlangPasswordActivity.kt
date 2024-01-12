package com.synrgy.travelid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import com.synrgy.travelid.R
import com.synrgy.travelid.databinding.ActivityAturUlangPasswordBinding
import com.synrgy.travelid.databinding.ActivityLupaPasswordBinding
import com.synrgy.travelid.databinding.FragmentAturUlangPasswordBSBinding
import com.synrgy.travelid.domain.model.auth.ResetPasswordRequest
import com.synrgy.travelid.domain.model.auth.UpdatePasswordRequest
import org.koin.androidx.viewmodel.ext.android.viewModel

class AturUlangPasswordActivity : AppCompatActivity() {

    private var bindingUpdatePassword: ActivityAturUlangPasswordBinding? = null
    private var bindingLupaPassword: ActivityLupaPasswordBinding? = null
    private val viewModel by viewModel<AturUlangPasswordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atur_ulang_password)
        bindingUpdatePassword = ActivityAturUlangPasswordBinding.inflate(layoutInflater)
        setContentView(bindingUpdatePassword?.root)

        //edit text 1 dan edit text 2
        observeViewModel()
        //buat viewmodel untuk put password baru nanti

    }

    private fun observeViewModel(){
        bindingUpdatePassword?.buttonKirimLupaPw?.setOnClickListener { handlePassword() }
        bindingUpdatePassword?.imgEyeNewPw?.setOnClickListener{ changeTextNewPassword() }
        bindingUpdatePassword?.imgEyeConfirmNewPw?.setOnClickListener{ changeTextConfirmPassword() }
        viewModel.error.observe(this, ::handleError)
    }
    private fun handlePassword() {
        val newPw = bindingUpdatePassword?.etAturNewPw?.text?.toString()?.trim()
        val confirmNewPw = bindingUpdatePassword?.etAturConfirmNewPw?.text?.toString()?.trim()
        val email = bindingLupaPassword?.etInputEmail?.text?.toString()
        //!!ambil otp hrsnya dari halaman otp tp karena msh dibuat kak bagus maka kt buat val biasa aja buat otpnya
        val otp = bindingUpdatePassword?.etOtp?.text?.toString()?.trim()
        val request = UpdatePasswordRequest(otp, email, newPw, confirmNewPw)

        if(newPw != null && confirmNewPw != null && newPw == confirmNewPw){
            if(isPasswordValid(newPw)){
                viewModel.updatePassword(request)
                val bottomSheetFragment = AturUlangPasswordBSFragment()
                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
            } else{
                bindingUpdatePassword?.tvWarningPwLemah?.visibility = View.VISIBLE
            }
        } else{
            bindingUpdatePassword?.tvWarningPwTdkSama?.visibility = View.VISIBLE
        }
    }

    private fun isPasswordValid(password: String):Boolean{
        if (password.length < 8){
            return false
        }
        val cekAngka = password.any{it.isDigit()}
        if (!cekAngka){
            return false
        }
        val cekSimbol = password.any{!it.isLetterOrDigit()}
        if (!cekSimbol){
            return false
        }
        return true
    }

    private fun changeTextNewPassword(){
        if (bindingUpdatePassword?.etAturNewPw?.transformationMethod == null){
            bindingUpdatePassword?.etAturNewPw?.transformationMethod = PasswordTransformationMethod.getInstance()
        } else{
            bindingUpdatePassword?.etAturNewPw?.transformationMethod = null
        }
    }

    private fun changeTextConfirmPassword(){
        if (bindingUpdatePassword?.etAturConfirmNewPw?.transformationMethod == null){
            bindingUpdatePassword?.etAturConfirmNewPw?.transformationMethod = PasswordTransformationMethod.getInstance()
        } else{
            bindingUpdatePassword?.etAturConfirmNewPw?.transformationMethod = null
        }
    }

    /*
    1.  tambah gambar eye password yg lain jadi berubah ketika diklik user
    2.  lanjut benerin yg border harus warna merah kalau error bakal muncul gitu

    !! kalau yg halaman otp udah jadi bisa pindahin halam BS yg cek email tadi ke arah otp, trus val otpnya diambil dari hal otp
     */

    private fun handleError(error: String?){
        bindingUpdatePassword?.etAturNewPw?.error = error
        bindingUpdatePassword?.etAturConfirmNewPw?.error = error
    }
}