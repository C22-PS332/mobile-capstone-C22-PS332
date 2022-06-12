package com.c22ps322.mealsnap.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.c22ps322.mealsnap.R
import com.c22ps322.mealsnap.databinding.IngredientCardBinding
import com.c22ps322.mealsnap.models.spoonacular.ExtendedIngredientsItem
import com.c22ps322.mealsnap.utils.OnItemCallbackInterface

class ListIngredientAdapter :
    ListAdapter<ExtendedIngredientsItem, ListIngredientAdapter.ViewHolder>(DiffCallBack) {

    lateinit var onItemCallbackInterface: OnItemCallbackInterface<ExtendedIngredientsItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val withBinding =
            IngredientCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(withBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: IngredientCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: ExtendedIngredientsItem) {
            binding.ingredientFrame.setOnClickListener { onItemCallbackInterface.onClick(ingredient) }

            Glide.with(itemView.context)
                .load(
                    Uri.parse("https://spoonacular.com/cdn/ingredients_100x100/${ingredient.image}")
                )
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_broken_image_24)
                .into(binding.ingredientImg)
        }
    }

    object DiffCallBack : DiffUtil.ItemCallback<ExtendedIngredientsItem>() {
        override fun areItemsTheSame(
            oldItem: ExtendedIngredientsItem,
            newItem: ExtendedIngredientsItem
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ExtendedIngredientsItem,
            newItem: ExtendedIngredientsItem
        ) = oldItem == newItem
    }
}