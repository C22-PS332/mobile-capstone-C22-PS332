package com.c22ps322.capstone.views.home

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.c22ps322.capstone.R
import com.c22ps322.capstone.databinding.FragmentHomeBinding
import com.c22ps322.capstone.utils.createFile
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class HomeFragment : Fragment(), View.OnClickListener, ImageCapture.OnImageSavedCallback {

    private lateinit var cameraExecutor: ExecutorService

    private var imageCapture: ImageCapture? = null

    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.captureBtn?.setOnClickListener(this)

        startCameraX()

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun startCameraX() {
        val cameraFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding?.viewFinder?.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    viewLifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )

            } catch (e: Exception) {

                binding?.root?.let {
                    Snackbar.make(
                        it,
                        getString(R.string.failed_to_start_camera),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null

        cameraExecutor.shutdown()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.capture_btn -> takePhoto()

            else -> return
        }
    }

    private fun takePhoto() {

        animateButton()

//        val imageCapture = imageCapture ?: return
//
//        val photoFile = createFile(requireActivity().application)
//
//        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
//
//        imageCapture.takePicture(
//            outputOptions,
//            ContextCompat.getMainExecutor(requireContext()),
//            this
//        )

        showResultSheet()
    }

    private fun showResultSheet() {
        childFragmentManager.findFragmentByTag("ResultSheetFragment")
            ?: ResultSheetFragment.newInstance().show(childFragmentManager, "ResultSheetFragment")
    }

    private fun animateButton() {
        ObjectAnimator.ofFloat(binding?.captureBtn, View.ROTATION, 0f, -90f).apply {
            duration = 1000
            start()
        }
    }

    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {}

    override fun onError(exception: ImageCaptureException) {
        binding?.root?.let {
            Snackbar.make(
                it,
                getString(R.string.failed_to_take_picture),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}