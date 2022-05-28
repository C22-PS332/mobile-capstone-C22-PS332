package com.c22ps322.capstone.utils

import androidx.core.content.res.ResourcesCompat
import com.c22ps322.capstone.R
import com.google.android.material.textfield.TextInputLayout


fun TextInputLayout.showSuccessValidationInput() {
    this.error = null

    this.endIconMode = TextInputLayout.END_ICON_CUSTOM

    this.endIconDrawable = ResourcesCompat.getDrawable(resources,
        R.drawable.ic_outline_check_circle_24,null
    )

    this.boxStrokeColor = ResourcesCompat.getColor(resources, R.color.success, null)
}

fun TextInputLayout.showErrorInput(message: String) {
    this.error = message

    this.boxStrokeColor = ResourcesCompat.getColor(resources, R.color.dark_orange, null)
}