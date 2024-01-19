package com.synrgy.travelid.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.synrgy.travelid.databinding.ActivityRegisterBinding
import com.synrgy.travelid.domain.model.UserRegisterRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var confirmFragment: ConfirmFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindView()
    }

    private fun bindView() {
        binding.btnRegister.setOnClickListener { handleRegisterValidation() }
    }



    private fun handleRegisterValidation() {
        val email = binding.etEmailInput.text.toString()
        val password = binding.etInputPassword.text.toString()
        val fullname = binding.etNameInput.text.toString()
        val phoneNumber = binding.etPhoneNumber.text.toString()
        val dateOfBirthday = binding.etBirthdayInput.text.toString()

        val request = UserRegisterRequest(email, password, fullname, phoneNumber, dateOfBirthday)

        viewModel.userRegister(request)

        confirmFragment = ConfirmFragment()
        confirmFragment.show(supportFragmentManager, "BSDialogFragment")
    }
}