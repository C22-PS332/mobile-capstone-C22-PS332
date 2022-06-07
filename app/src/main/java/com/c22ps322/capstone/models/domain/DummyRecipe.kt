package com.c22ps322.capstone.models.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DummyRecipe(
    val id: Int,

    val title: String,

    val desc: String,

    val imageUrl: String,

    val ingredients: ArrayList<String>
): Parcelable