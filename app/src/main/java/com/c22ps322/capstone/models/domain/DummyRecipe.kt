package com.c22ps322.capstone.models.domain

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class DummyRecipe(
    val title: String,

    val desc: String,

    val imageUrl: String,

    val ingredients: ArrayList<String>
): Parcelable {
    @IgnoredOnParcel
    val id: UUID = UUID.randomUUID()
}
