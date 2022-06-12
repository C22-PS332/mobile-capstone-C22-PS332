package com.c22ps322.mealsnap.views.home

import android.animation.ObjectAnimator
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.c22ps322.mealsnap.R
import com.c22ps322.mealsnap.databinding.FragmentHomeBinding
import com.c22ps322.mealsnap.models.enums.CameraOption
import com.c22ps322.mealsnap.utils.createFile
import com.c22ps322.mealsnap.utils.createTempFile
import com.c22ps322.mealsnap.utils.uriToFile
import com.c22ps322.mealsnap.viewmodels.ListRecipeViewModel
import com.c22ps322.mealsnap.viewmodels.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@AndroidEntryPoint
class HomeFragment : Fragment(), View.OnClickListener, ImageCapture.OnImageSavedCallback {

    private val listRecipeViewModel by activityViewModels<ListRecipeViewModel>()

    private val settingViewModel by viewModels<SettingViewModel>()

    private var cameraExecutor: ExecutorService? = null

    private var imageCapture: ImageCapture? = null

    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding

    private lateinit var imagePath: String

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

        loadSettings()
    }

    private fun loadSettings() {
        settingViewModel.getCameraOption().observe(viewLifecycleOwner) { setupCamera(it) }
    }

    private fun setupCamera(cameraOption: String) {
        when (cameraOption) {
            CameraOption.CAMERA_X -> {

                binding?.apply {
                    captureBtn.isVisible = true

                    viewFinder.isVisible = true

                    openSystemCameraBtn.isVisible = false

                    captureBtn.setOnClickListener(this@HomeFragment)
                }

                startCameraX()

                cameraExecutor = Executors.newSingleThreadExecutor()
            }

            CameraOption.SYSTEM -> {

                binding?.apply {
                    captureBtn.isVisible = false

                    viewFinder.isVisible = false

                    openSystemCameraBtn.isVisible = true

                    openSystemCameraBtn.setOnClickListener(this@HomeFragment)
                }
            }

            else -> return
        }
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

                Toast.makeText(
                    requireContext(),
                    getString(R.string.failed_to_start_camera),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null

        cameraExecutor?.shutdown()

        if (uploadJob.isActive) uploadJob.cancel()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.capture_btn -> takePhoto()

            R.id.open_system_camera_btn -> openCameraSystem()

            else -> return
        }
    }

    private fun openCameraSystem() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val packageManager = activity?.packageManager

        packageManager?.let {
            intent.resolveActivity(it)

            createTempFile(requireContext()).also { file ->
                val photoURI: Uri = FileProvider.getUriForFile(
                    requireActivity(),
                    "com.c22ps322.mealsnap",
                    file
                )
                imagePath = file.absolutePath

                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

                launcherIntentCamera.launch(intent)
            }
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {

            val file = File(imagePath)

            uploadImage(file)
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
    }

    private fun uploadImage(file: File) {

        val token = listRecipeViewModel.getToken()

        if (token.isNullOrBlank()) {
            Toast.makeText(
                requireContext(),
                getString(R.string.please_relogin),
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPreview(file))

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            if (uploadJob.isActive) uploadJob.cancel()

            uploadJob = launch { listRecipeViewModel.uploadImage(token, file) }
        }
    }

    private fun animateButton() {
        ObjectAnimator.ofFloat(binding?.captureBtn, View.ROTATION, 0f, -90f).apply {
            duration = 1000
            start()
        }
    }

    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {

        val savedUri = outputFileResults.savedUri ?: return

        val file = uriToFile(savedUri, requireContext())

        uploadImage(file)
    }

    override fun onError(exception: ImageCaptureException) {
        Toast.makeText(
            requireContext(),
            getString(R.string.failed_to_take_picture),
            Toast.LENGTH_SHORT
        ).show()
    }
}