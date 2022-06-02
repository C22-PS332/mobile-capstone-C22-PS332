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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.c22ps322.capstone.R
import com.c22ps322.capstone.databinding.FragmentHomeBinding
import com.c22ps322.capstone.models.domain.DummyRecipe
import com.c22ps322.capstone.models.enums.NetworkResult
import com.c22ps322.capstone.utils.createFile
import com.c22ps322.capstone.viewmodels.ListRecipeViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@AndroidEntryPoint
class HomeFragment : Fragment(), View.OnClickListener, ImageCapture.OnImageSavedCallback {

    private val listRecipeViewModel by viewModels<ListRecipeViewModel>()

    private lateinit var cameraExecutor: ExecutorService

    private var imageCapture: ImageCapture? = null

    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding

    private var uploadJob: Job = Job()

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

        if (uploadJob.isActive) uploadJob.cancel()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.capture_btn -> takePhoto()

            else -> return
        }
    }

    private fun takePhoto() {

        animateButton()

        val imageCapture = imageCapture ?: return

        val photoFile = createFile(requireActivity().application)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            this
        )

        uploadImage(photoFile)
    }

    private fun uploadImage(file: File){

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            if (uploadJob.isActive) uploadJob.cancel()

            uploadJob = launch {

                val uploadFlow = listRecipeViewModel.uploadImage(file)

                uploadFlow.collect{ result ->
                    when (result) {
                        is NetworkResult.Loading -> {}

                        is NetworkResult.Success -> {
                            showResultSheet(result.data)
                        }

                        is NetworkResult.Error -> {

                            binding?.root?.let {
                                Snackbar.make(
                                    it,
                                    result.message,
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showResultSheet(listRecipe: ArrayList<DummyRecipe>) {
        childFragmentManager.findFragmentByTag("ResultSheetFragment")
            ?: ResultSheetFragment.newInstance(listRecipe).show(childFragmentManager, "ResultSheetFragment")
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