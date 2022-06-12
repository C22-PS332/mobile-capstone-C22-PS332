package com.c22ps322.mealsnap.views.home

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.c22ps322.mealsnap.R
import com.c22ps322.mealsnap.adapters.ListRecipeAdapter
import com.c22ps322.mealsnap.databinding.FragmentResultSheetBinding
import com.c22ps322.mealsnap.models.domain.Recipe
import com.c22ps322.mealsnap.models.enums.NetworkResult
import com.c22ps322.mealsnap.utils.OnItemCallbackInterface
import com.c22ps322.mealsnap.viewmodels.RecipeInformationViewModel
import com.c22ps322.mealsnap.views.DetailFoodActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResultSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentResultSheetBinding? = null

    private val binding get() = _binding

    private var listRecipe: ArrayList<Recipe>? = arrayListOf()

    private val recipeInformationViewModel by viewModels<RecipeInformationViewModel>()

    private var detailJob: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            listRecipe = it.getParcelableArrayList(ARG_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultSheetBinding.inflate(inflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecipeList()
    }

    private fun setupRecipeList() {

        if (listRecipe?.isEmpty() == true) {

            binding?.apply {
                errorTv.isVisible = true

                nestedList.isVisible = false
            }
            return
        }

        val listRecipeAdapter = ListRecipeAdapter()

        listRecipeAdapter.onItemCallback = object : OnItemCallbackInterface<Recipe> {
            override fun onClick(item: Recipe) {

                if (detailJob.isActive) detailJob.cancel()

                viewLifecycleOwner.lifecycleScope.launch {

                    detailJob = launch {
                        val detailFlow = recipeInformationViewModel.getRecipeInformation(item.id)

                        detailFlow.collect { result ->
                            when (result) {
                                is NetworkResult.Loading -> {
                                    binding?.errorTv?.isVisible = false

                                    binding?.progressHorizontal?.isVisible = true
                                }

                                is NetworkResult.Success -> {
                                    binding?.errorTv?.isVisible = false

                                    binding?.progressHorizontal?.hide()

                                    val intent =
                                        Intent(requireContext(), DetailFoodActivity::class.java)

                                    intent.putExtra(DetailFoodActivity.RECIPE_PARAM, result.data)

                                    startActivity(intent)
                                }

                                is NetworkResult.Error -> {

                                    binding?.errorTv?.text = getString(R.string.error_loading_data)

                                    binding?.errorTv?.isVisible = true

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
            }
        }

        binding?.recipeList?.adapter = listRecipeAdapter

        listRecipeAdapter.submitList(listRecipe)
    }

    override fun onStart() {
        super.onStart()

        val view: FrameLayout =
            dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!

        view.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT

        val behavior = BottomSheetBehavior.from(view)

        behavior.peekHeight = 3000

        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onCancel(dialog: DialogInterface) {
        dialog.dismiss()
        super.onCancel(dialog)
    }

    companion object {
        private const val ARG_PARAM = "list-recipe"

        @JvmStatic
        fun newInstance(listRecipe: ArrayList<Recipe>) =
            ResultSheetFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_PARAM, listRecipe)
                }
            }
    }
}