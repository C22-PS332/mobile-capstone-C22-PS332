package com.c22ps322.capstone.views.detail

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.c22ps322.capstone.R
import com.c22ps322.capstone.databinding.FragmentNutritionBinding
import com.c22ps322.capstone.models.edamam.EdamamResponse
import com.c22ps322.capstone.models.enums.NetworkResult
import com.c22ps322.capstone.viewmodels.NutritionViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


private const val ARG_PARAM = "ingredients"

@AndroidEntryPoint
class NutritionFragment : Fragment() {

    private var _binding: FragmentNutritionBinding? = null

    private val binding get() = _binding

    private var ingredients: ArrayList<String>? = arrayListOf()

    private val nutritionViewModel by viewModels<NutritionViewModel>()

    private var nutritionJob: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ingredients = it.getStringArrayList(ARG_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNutritionBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadNutrition()
    }

    private fun loadNutrition() {
        if (nutritionJob.isActive) nutritionJob.cancel()

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            nutritionJob = launch {
                val nutritionFLow = nutritionViewModel.getNutrition(ingredients.orEmpty())

                nutritionFLow.collect { result ->
                    when (result) {
                        is NetworkResult.Loading -> {
                            binding?.errorTv?.isVisible = false

                            binding?.progressBar?.isVisible = true
                        }

                        is NetworkResult.Success -> {
                            binding?.errorTv?.isVisible = false

                            binding?.tableLayout?.isVisible = true

                            setupTableLayout(result.data)

                            binding?.progressBar?.isVisible = false
                        }

                        is NetworkResult.Error -> {

                            binding?.errorTv?.isVisible = true

                            binding?.progressBar?.isVisible = false

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

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null

        if (nutritionJob.isActive) nutritionJob.cancel()
    }

    private fun setupTableLayout(edamamResponse: EdamamResponse) {

        binding?.caloriesTv?.text = edamamResponse.calories.toString()

        binding?.totalWeightTv?.text = getString(
            R.string.percentage_placeholder,
            edamamResponse.totalWeight.roundToInt().toString(),
            'g'
        )

        val totalDaily = edamamResponse.totalDaily

        val typeInterface = ResourcesCompat.getFont(requireContext(), R.font.poppins_medium)

        val greyTextColor = ResourcesCompat.getColor(resources, R.color.grey, null)

        val greyTableRowColor = ResourcesCompat.getColor(resources, R.color.table_row, null)

        val blackTextColor = ResourcesCompat.getColor(resources, R.color.black, null)

        var count = 0

        for ((_, value) in totalDaily.toMap()) {
            val tableRow = TableRow(requireContext())

            value?.let {

                count++

                tableRow.apply {
                    gravity = Gravity.CENTER

                    if (count % 2 == 0) setBackgroundColor(greyTableRowColor)
                }

                val titleTextView = TextView(requireContext())

                titleTextView.apply {
                    typeface = typeInterface

                    textSize = 16f

                    textAlignment = View.TEXT_ALIGNMENT_CENTER

                    setTextColor(greyTextColor)
                }

                val valueTextView = TextView(requireContext())

                valueTextView.apply {
                    typeface = typeInterface

                    textSize = 16f

                    textAlignment = View.TEXT_ALIGNMENT_CENTER

                    setTextColor(blackTextColor)
                }

                titleTextView.text = value.label

                valueTextView.text = getString(
                    R.string.percentage_placeholder,
                    value.quantity.roundToInt().toString(),
                    value.unit
                )

                tableRow.addView(titleTextView)

                tableRow.addView(valueTextView)
            }

            binding?.tableLayout?.addView(
                tableRow
            )
        }

        if (count == 0) {

            val tableRow = TableRow(requireContext())

            tableRow.gravity = Gravity.CENTER

            val titleTextView = TextView(requireContext())

            titleTextView.apply {
                typeface = typeInterface

                textSize = 16f

                textAlignment = View.TEXT_ALIGNMENT_CENTER

                setTextColor(greyTextColor)
            }

            val valueTextView = TextView(requireContext())

            valueTextView.apply {
                typeface = typeInterface

                textSize = 16f

                textAlignment = View.TEXT_ALIGNMENT_CENTER

                setTextColor(blackTextColor)
            }

            titleTextView.text = getString(R.string.no_data)

            valueTextView.text = getString(R.string.no_data)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(ingredients: ArrayList<String>) =
            NutritionFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(ARG_PARAM, ingredients)
                }
            }
    }
}