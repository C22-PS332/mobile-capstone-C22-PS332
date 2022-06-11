package com.c22ps322.capstone.views

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.c22ps322.capstone.R
import com.c22ps322.capstone.databinding.ActivityWelcomeBinding
import com.c22ps322.capstone.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityWelcomeBinding
    private val loginViewModel by viewModels<LoginViewModel>()
    private var welcomeJob: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val button = binding.button

        button.setOnClickListener(this)

        // checking session
        lifecycleScope.launchWhenCreated {
            if (welcomeJob.isActive) welcomeJob.cancel()

            welcomeJob = launch {

                if (loginViewModel.isLoggedIn()) {
                    val mainIntent = Intent(this@WelcomeActivity, MainActivity::class.java)
                    startActivity(mainIntent)

                    finish()
                }
            }
        }
    }

    private fun requestCameraPermission() {
        if (isCameraPermissionGranted()) startCamera() else requestPermission.launch(
            REQUIRED_CAMERA_PERMISSION
        )
    }

    private fun startCamera() {
        val intent = Intent(this, LoginActivity::class.java)

        startActivity(intent)
    }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permission ->
            if (permission == true) startCamera()
            else Toast.makeText(
                applicationContext,
                getString(R.string.permission_denied),
                Toast.LENGTH_SHORT
            ).show()
        }

    private fun isCameraPermissionGranted() = ContextCompat.checkSelfPermission(
        this,
        REQUIRED_CAMERA_PERMISSION
    ) == PackageManager.PERMISSION_GRANTED


    companion object {
        private const val REQUIRED_CAMERA_PERMISSION = Manifest.permission.CAMERA
    }

    override fun onClick(v: View) {
        requestCameraPermission()
    }
}