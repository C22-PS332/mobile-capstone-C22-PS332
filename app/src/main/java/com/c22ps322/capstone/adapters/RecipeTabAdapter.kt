package com.c22ps322.capstone.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.c22ps322.capstone.models.spoonacular.ExtendedIngredientsItem
import com.c22ps322.capstone.models.spoonacular.Nutrition
import com.c22ps322.capstone.views.detail.DetailRecipeFragment
import com.c22ps322.capstone.views.detail.NutritionFragment

class RecipeTabAdapter(
    fragment: FragmentActivity,
    private val summary: String,
    private val sourceUrl: String,
    private val nutrition: Nutrition
) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DetailRecipeFragment.newInstance(summary, sourceUrl)
            else -> NutritionFragment.newInstance(nutrition)
        }
    }
}