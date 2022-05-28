package com.c22ps322.capstone.views.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.c22ps322.capstone.R
import com.c22ps322.capstone.databinding.ActivityChangePasswordBinding
import com.c22ps322.capstone.utils.showErrorInput

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()

        setupInputValidation()
    }

    private fun setupInputValidation() {

        binding.apply {
            this.newPasswordEt.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { validateNewPassword(s) }

                override fun afterTextChanged(s: Editable?) {}
            })

            this.retypeNewPasswordEt.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { validateRetypedPassword(s) }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    private fun validateRetypedPassword(password: CharSequence) {
        binding.newPasswordEt.text.let {
            if (it.toString() != password.toString() && password.isNotEmpty()) binding.retypeNewPasswordLabel.showErrorInput(getString(R.string.error_new_password))
            else binding.retypeNewPasswordLabel.error = null
        }
    }

    private fun validateNewPassword(password: CharSequence) {

        if(password.length < 8 && password.isNotEmpty()) binding.newPasswordLabel.showErrorInput(getString(R.string.error_change_password))
        else binding.newPasswordLabel.error = null
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