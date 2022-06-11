package com.c22ps322.capstone.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.c22ps322.capstone.R
import com.c22ps322.capstone.databinding.ActivityLoginBinding
import com.c22ps322.capstone.models.enums.NetworkResult
import com.c22ps322.capstone.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding

    private val loginViewModel by viewModels<LoginViewModel>()

    private var loginJob: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.registerNowTv.setOnClickListener(this)

        binding.logInBtn.setOnClickListener(this)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loginPg.isVisible = isLoading
    }

    private fun login(email: String, password: String) {
        lifecycleScope.launchWhenResumed {
            if (loginJob.isActive) loginJob.cancel()

            loginJob = launch {

                loginViewModel.login(email, password).collect { result ->
                    when (result) {
                        is NetworkResult.Loading -> showLoading(true)

                        is NetworkResult.Success -> {
                            showLoading(false)

                            Toast.makeText(
                                this@LoginActivity,
                                getString(R.string.login_successfull),
                                Toast.LENGTH_SHORT
                            ).show()

                            val mainIntent = Intent(this@LoginActivity, MainActivity::class.java)

                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

                            startActivity(mainIntent)

                            finish()
                        }

                        is NetworkResult.Error -> {
                            showLoading(false)

                            Toast.makeText(this@LoginActivity, result.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.register_now_tv -> {
                val regIntent = Intent(this@LoginActivity, RegisterActivity::class.java)

                startActivity(regIntent)

                finish()
            }

            R.id.log_in_btn -> {
                if (binding.emailEt.text.isNullOrBlank()) {
                    Toast.makeText(this, getString(R.string.email_is_required), Toast.LENGTH_SHORT)
                        .show()

                    return
                }

                if (binding.passwordEt.text.isNullOrBlank()) {
                    Toast.makeText(this, getString(R.string.error_old_password), Toast.LENGTH_SHORT)
                        .show()

                    return
                }

                val emailFinal = binding.emailEt.text.toString().trim()

                val passwordFinal = binding.passwordEt.text.toString()

                login(emailFinal, passwordFinal)

                hideKeyBoard()
            }

            else -> return
        }
    }

    private fun hideKeyBoard() {
        val currentFocus = currentFocus ?: return

        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}