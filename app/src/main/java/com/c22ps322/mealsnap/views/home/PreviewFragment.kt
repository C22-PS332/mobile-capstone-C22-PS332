package com.c22ps322.mealsnap.views.home

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.c22ps322.mealsnap.R
import com.c22ps322.mealsnap.databinding.FragmentPreviewBinding
import com.c22ps322.mealsnap.models.domain.Recipe
import com.c22ps322.mealsnap.models.enums.NetworkResult
import com.c22ps322.mealsnap.viewmodels.ListRecipeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PreviewFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentPreviewBinding? = null

    private val binding get() = _binding

    private val navArgs: PreviewFragmentArgs by navArgs()

    private val listRecipeViewModel by activityViewModels<ListRecipeViewModel>()

    private var uploadJob: Job = Job()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPreviewBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayPicture()

        binding?.recaptureBtn?.setOnClickListener(this)

        listRecipeViewModel.startCollectFlow.observe(viewLifecycleOwner) {
            if (it == true) collectFlow()
        }
    }

    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {

            uploadJob = launch {

                listRecipeViewModel.uploadFlow.collect { result ->
                    when (result) {
                        is NetworkResult.Loading -> {

                            binding?.toolbarFragment?.title = getString(R.string.uploading)

                            binding?.progressHorizontal?.isVisible = true
                        }

                        is NetworkResult.Success -> {
                            showResultSheet(result.data)

                            binding?.toolbarFragment?.title = getString(R.string.preview)

                            binding?.progressHorizontal?.hide()

                        }

                        is NetworkResult.Error -> {

                            binding?.toolbarFragment?.title = getString(R.string.preview)

                            binding?.progressHorizontal?.hide()

                            Toast.makeText(
                                requireContext(),
                                result.message,
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                }
            }
        }

        listRecipeViewModel.doneCollectFlow()
    }

    private fun showResultSheet(listRecipe: ArrayList<Recipe>) {
        childFragmentManager.findFragmentByTag("ResultSheetFragment")
            ?: ResultSheetFragment.newInstance(listRecipe)
                .show(childFragmentManager, "ResultSheetFragment")
    }

    private fun displayPicture() {
        val file = navArgs.file

        val bitmap = BitmapFactory.decodeFile(file.path)

        binding?.previewImg?.setImageBitmap(bitmap)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null

        if (uploadJob.isActive) uploadJob.cancel()
    }

    override fun onClick(v: View) {
        findNavController().navigate(PreviewFragmentDirections.actionPreviewToHomeFragment())
    }
}