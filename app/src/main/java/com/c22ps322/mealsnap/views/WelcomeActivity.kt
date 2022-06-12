package com.c22ps322.mealsnap.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.c22ps322.mealsnap.databinding.ActivityWelcomeBinding
import com.c22ps322.mealsnap.viewmodels.LoginViewModel
import com.c22ps322.mealsnap.viewmodels.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityWelcomeBinding

    private val loginViewModel by viewModels<LoginViewModel>()

    private val settingViewModel by viewModels<SettingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)

        setFirstScreen(binding)

        val button = binding.button

        button.setOnClickListener(this)
    }

    private fun setFirstScreen(binding: ActivityWelcomeBinding) {
        if (loginViewModel.isLoggedIn()) {
            val mainIntent = Intent(this@WelcomeActivity, MainActivity::class.java)
            startActivity(mainIntent)

            finish()

            return
        }

        settingViewModel.isFirstTime().observe(this) { isFirst ->
            if (isFirst) {
                setContentView(binding.root)

                return@observe
            }

            openLoginActivity()
        }
    }

    private fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)

        startActivity(intent)

        finish()
    }

    override fun onClick(v: View) {
        settingViewModel.setAfterFirstOpen()
    }
}