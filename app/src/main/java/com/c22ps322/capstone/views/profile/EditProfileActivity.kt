package com.c22ps322.capstone.views.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.c22ps322.capstone.R
import com.c22ps322.capstone.databinding.ActivityEditProfileBinding
import com.c22ps322.capstone.utils.showErrorInput
import com.c22ps322.capstone.utils.showSuccessValidationInput

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()

        setupInputValidation()
    }

    private fun setupInputValidation() {

        binding.fullNameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validateFullName(s)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.contactNumberEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validateContactNumber(s)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.emailEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validateEmail(s)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun validateContactNumber(number: CharSequence) {
        if (TextUtils.isEmpty(number))
            binding.contactNumberLabel.showErrorInput(getString(R.string.error_contact_number))
        else binding.contactNumberLabel.showSuccessValidationInput()
    }

    private fun validateEmail(email: CharSequence) {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            binding.emailLabel.showErrorInput(
                getString(R.string.error_email)
            ) else binding.emailLabel.showSuccessValidationInput()
    }

    private fun validateFullName(name: CharSequence) {
        if (TextUtils.isEmpty(name))
            binding.fullNameLabel.showErrorInput(getString(R.string.error_full_name))
        else binding.fullNameLabel.showSuccessValidationInput()
    }


    private fun setupNavigation() {
        setSupportActionBar(binding.toolbarFragment)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}