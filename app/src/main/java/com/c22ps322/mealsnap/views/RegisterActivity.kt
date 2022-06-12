package com.c22ps322.mealsnap.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.c22ps322.mealsnap.R
import com.c22ps322.mealsnap.databinding.ActivityRegisterBinding
import com.c22ps322.mealsnap.models.enums.NetworkResult
import com.c22ps322.mealsnap.viewmodels.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterBinding

    private var nameFinal: String = ""

    private var emailFinal: String = ""

    private var passwordFinal: String = ""

    private val registerViewModel by viewModels<RegisterViewModel>()

    private var registerJob: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // add listener to name edittext
        binding.nameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!isNameValid(binding.nameEt.text)) {
                    binding.nameTil.helperText = getString(R.string.error_full_name)
                } else {
                    binding.nameTil.helperText = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                nameFinal = binding.nameEt.text.toString()
            }
        })

        // add listener to email edittext
        binding.emailEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!isEmailValid(binding.emailEt.text.toString())) {
                    binding.emailTil.helperText = getString(R.string.error_email)
                } else {
                    binding.emailTil.helperText = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                emailFinal = binding.emailEt.text.toString()
            }
        })

        // add listener to password edittext
        binding.passwordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!isPasswordValid(binding.passwordEt.text!!.toString())) {
                    binding.passwordTil.helperText = getString(R.string.error_new_password)
                } else {
                    binding.passwordTil.helperText = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                passwordFinal = binding.passwordEt.text.toString()
            }
        })

        // add listener for directing to login page
        binding.loginNowTv.setOnClickListener(this)
        // add listener for post register
        binding.registerBtn.setOnClickListener(this)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.registerPg.isVisible = isLoading
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }

    private fun isNameValid(name: Editable?): Boolean {
        return !name.isNullOrBlank()
    }

    private fun register(name: String, email: String, password: String) {
        lifecycleScope.launchWhenResumed {
            if (registerJob.isActive) registerJob.cancel()

            registerJob = launch {

                registerViewModel.register(name, email, password).collect { result ->
                    when (result) {
                        is NetworkResult.Loading -> showLoading(true)

                        is NetworkResult.Success -> {
                            showLoading(false)

                            Toast.makeText(
                                this@RegisterActivity,
                                result.data.status.orEmpty(),
                                Toast.LENGTH_SHORT
                            ).show()

                            val mainIntent =
                                Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(mainIntent)

                            finish()
                        }

                        is NetworkResult.Error -> {
                            showLoading(false)

                            Toast.makeText(
                                this@RegisterActivity,
                                result.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.login_now_tv -> {
                val loginIntent = Intent(this@RegisterActivity, LoginActivity::class.java)

                startActivity(loginIntent)

                finish()
            }

            R.id.register_btn -> {

                if (!isNameValid(binding.nameEt.text)) {
                    binding.nameTil.helperText = getString(R.string.error_full_name)

                    Toast.makeText(
                        this,
                        getString(R.string.error_full_name),
                        Toast.LENGTH_SHORT
                    ).show()

                    return
                }

                if (!isEmailValid(binding.emailEt.text.toString())) {
                    binding.emailTil.helperText = getString(R.string.error_email)

                    Toast.makeText(
                        this,
                        getString(R.string.error_email),
                        Toast.LENGTH_SHORT
                    ).show()

                    return
                }

                if (!isPasswordValid(binding.passwordEt.text!!.toString())) {
                    binding.passwordTil.helperText = getString(R.string.error_old_password)

                    Toast.makeText(
                        this,
                        getString(R.string.error_old_password),
                        Toast.LENGTH_SHORT
                    ).show()

                    return
                }

                register(nameFinal, emailFinal, passwordFinal)

                hideKeyBoard()
            }

            else -> return
        }
    }

    private fun hideKeyBoard() {
        val currentFocus = currentFocus ?: return

        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}