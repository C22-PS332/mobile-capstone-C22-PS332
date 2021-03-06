package com.c22ps322.mealsnap.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.c22ps322.mealsnap.R
import com.c22ps322.mealsnap.databinding.RecipeCardBinding
import com.c22ps322.mealsnap.models.domain.Recipe
import com.c22ps322.mealsnap.utils.OnItemCallbackInterface

class ListRecipeAdapter : ListAdapter<Recipe, ListRecipeAdapter.ViewHolder>(DiffCallBack) {

    lateinit var onItemCallback: OnItemCallbackInterface<Recipe>

    inner class ViewHolder(private val binding: RecipeCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dummyRecipe: Recipe) {

            Glide.with(itemView.context)
                .load(dummyRecipe.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_broken_image_24)
                .into(binding.recipeImg)

            binding.recipeNameTv.text = dummyRecipe.title

            binding.recipeDescTv.text = dummyRecipe.summary

            binding.recipeCard.setOnClickListener { onItemCallback.onClick(dummyRecipe) }
        }
    }

    object DiffCallBack : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val withBinding: RecipeCardBinding =
            RecipeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(withBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}