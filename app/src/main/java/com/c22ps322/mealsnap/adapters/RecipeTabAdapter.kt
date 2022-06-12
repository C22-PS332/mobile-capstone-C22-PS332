package com.c22ps322.mealsnap.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.c22ps322.mealsnap.models.spoonacular.Nutrition
import com.c22ps322.mealsnap.views.detail.DetailRecipeFragment
import com.c22ps322.mealsnap.views.detail.NutritionFragment

class RecipeTabAdapter(
    fragment: FragmentActivity,
    private val summary: String,
    private val sourceUrl: String,
    private val nutrition: Nutrition?
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