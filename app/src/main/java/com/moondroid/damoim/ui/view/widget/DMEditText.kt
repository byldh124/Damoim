package com.moondroid.damoim.ui.view.widget

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import androidx.core.content.ContextCompat
import com.moondroid.damoim.R

class DMEditText : androidx.appcompat.widget.AppCompatEditText {

    constructor(context: Context?) : super(context!!) {
        init()
    }


    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        if (text.isNullOrEmpty()) {
            background = ContextCompat.getDrawable(context, R.drawable.bg_edit_01)
            setHintTextColor(ContextCompat.getColor(context, R.color.blue_dark01))
        } else {
            background = ContextCompat.getDrawable(context, R.drawable.bg_edit_02)
            setTextColor(ContextCompat.getColor(context, R.color.white))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            textCursorDrawable = ContextCompat.getDrawable(context, R.drawable.cursor)
        }
    }

    override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection? {
        val ic = super.onCreateInputConnection(outAttrs)
        outAttrs.imeOptions = outAttrs.imeOptions or 268435456
        outAttrs.imeOptions = outAttrs.imeOptions or 33554432
        return ic
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused) {
            background = ContextCompat.getDrawable(context, R.drawable.bg_edit_03)
            setTextColor(ContextCompat.getColor(context, R.color.blue_dark01))
        } else {
            if (text.isNullOrEmpty()) {
                background = ContextCompat.getDrawable(context, R.drawable.bg_edit_01)
                setHintTextColor(ContextCompat.getColor(context, R.color.blue_dark01))
            } else {
                background = ContextCompat.getDrawable(context, R.drawable.bg_edit_02)
                setTextColor(ContextCompat.getColor(context, R.color.white))
            }
        }

    }
}