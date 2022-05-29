package com.c22ps322.capstone.views

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.c22ps322.capstone.R

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            requestCameraPermission()
        }
    }

    private fun requestCameraPermission(){

        if (isCameraPermissionGranted()) startCamera() else requestPermission.launch(
            REQUIRED_CAMERA_PERMISSION)

    }

    private fun startCamera() {
        val intent = Intent(this, MainActivity::class.java)

        startActivity(intent)
    }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permission ->
            if (permission == true) {
                startCamera()

            } else {

                Toast.makeText(applicationContext, "Permission tidak diberikan", Toast.LENGTH_SHORT).show()
            }
        }

    private fun isCameraPermissionGranted() = ContextCompat.checkSelfPermission(this, REQUIRED_CAMERA_PERMISSION) == PackageManager.PERMISSION_GRANTED


    companion object {
        private const val REQUIRED_CAMERA_PERMISSION = Manifest.permission.CAMERA
    }
}