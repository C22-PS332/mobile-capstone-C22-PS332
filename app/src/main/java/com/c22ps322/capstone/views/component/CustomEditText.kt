package com.c22ps322.capstone.views.component

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import com.c22ps322.capstone.R
import com.google.android.material.textfield.TextInputEditText

class CustomEditText: TextInputEditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        this.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.white, null))

        setPaddingRelative(0, 60, 0, paddingBottom)
    }
}