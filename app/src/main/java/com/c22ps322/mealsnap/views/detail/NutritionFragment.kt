package com.c22ps322.mealsnap.views.detail

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.c22ps322.mealsnap.R
import com.c22ps322.mealsnap.databinding.FragmentNutritionBinding
import com.c22ps322.mealsnap.models.spoonacular.Nutrition
import kotlin.math.roundToInt

class NutritionFragment : Fragment() {

    private var _binding: FragmentNutritionBinding? = null

    private val binding get() = _binding

    private var nutrition: Nutrition? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nutrition = it.getParcelable(ARG_PARAM)
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

        nutrition?.let { setupTableLayout(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun setupTableLayout(nutrition: Nutrition) {

        binding?.totalWeightTv?.text = getString(
            R.string.percentage_placeholder,
            nutrition.weightPerServing?.amount?.roundToInt().toString(),
            nutrition.weightPerServing?.unit.orEmpty()
        )

        val typeInterface = ResourcesCompat.getFont(requireContext(), R.font.poppins_medium)

        val greyTextColor = ResourcesCompat.getColor(resources, R.color.grey, null)

        val greyTableRowColor = ResourcesCompat.getColor(resources, R.color.table_row, null)

        val blackTextColor = ResourcesCompat.getColor(resources, R.color.black, null)

        val nutrients = nutrition.nutrients.orEmpty()

        if (nutrients.isEmpty()) {
            val tableRow = TableRow(requireContext())

            tableRow.gravity = Gravity.CENTER

            val titleTextView = TextView(requireContext())

            titleTextView.apply {
                typeface = typeInterface

                textSize = 16f

                textAlignment = View.TEXT_ALIGNMENT_CENTER

                setTextColor(greyTextColor)
            }

            val amountTextView = TextView(requireContext())

            amountTextView.apply {
                typeface = typeInterface

                textSize = 16f

                textAlignment = View.TEXT_ALIGNMENT_CENTER

                setTextColor(blackTextColor)
            }

            val valueTextView = TextView(requireContext())

            valueTextView.apply {
                typeface = typeInterface

                textSize = 16f

                textAlignment = View.TEXT_ALIGNMENT_CENTER

                setTextColor(blackTextColor)
            }

            titleTextView.text = getString(R.string.no_data)

            amountTextView.text = getString(R.string.no_data)

            valueTextView.text = getString(R.string.no_data)

            tableRow.addView(titleTextView)

            tableRow.addView(amountTextView)

            tableRow.addView(valueTextView)

            binding?.tableLayout?.addView(
                tableRow
            )

            return
        }

        var count = 0

        nutrients.forEach { item ->
            val tableRow = TableRow(requireContext())

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

            val amountTextView = TextView(requireContext())

            amountTextView.apply {
                typeface = typeInterface

                textSize = 16f

                textAlignment = View.TEXT_ALIGNMENT_CENTER

                setTextColor(blackTextColor)
            }

            val valueTextView = TextView(requireContext())

            valueTextView.apply {
                typeface = typeInterface

                textSize = 16f

                textAlignment = View.TEXT_ALIGNMENT_CENTER

                setTextColor(blackTextColor)
            }

            titleTextView.text = item.name.orEmpty()

            amountTextView.text = getString(
                R.string.percentage_placeholder,
                item.amount?.roundToInt().toString(),
                item.unit.orEmpty()
            )

            valueTextView.text = getString(
                R.string.percentage_placeholder,
                item.percentOfDailyNeeds?.roundToInt().toString(),
                '%'
            )

            tableRow.addView(titleTextView)

            tableRow.addView(amountTextView)

            tableRow.addView(valueTextView)

            binding?.tableLayout?.addView(
                tableRow
            )
        }
    }

    companion object {
        private const val ARG_PARAM = "nutrition"

        @JvmStatic
        fun newInstance(ingredients: Nutrition?) =
            NutritionFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM, ingredients)
                }
            }
    }
}