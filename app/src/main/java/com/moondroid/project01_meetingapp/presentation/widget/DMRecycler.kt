package com.moondroid.project01_meetingapp.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.utils.DMUtils
import com.moondroid.project01_meetingapp.utils.gone

class DMRecycler : RecyclerView {

    lateinit var emptyView: TextView
    private var initialized = false

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    fun setEmptyText(text: CharSequence) {
        emptyView.text = text
    }

    private fun init(context: Context) {
        emptyView = TextView(context).apply {
            text = context.getString(R.string.alm_data_empty)
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.0f)
            setTextColor(ContextCompat.getColor(context, R.color.gray_dark01))
            gravity = Gravity.CENTER
            typeface = ResourcesCompat.getFont(context, R.font.nanum_square_round_eb)
        }
        val podding = DMUtils.dpToPixel(context, 16)
        emptyView.setPadding(0 ,podding ,0 ,podding)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (!initialized) {
            parent?.let {
                when (parent) {
                    is ConstraintLayout -> {
                        val layoutParams = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_PARENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                        ).apply {
                            topToTop = ConstraintSet.PARENT_ID
                            bottomToBottom = ConstraintSet.PARENT_ID
                        }

                        (parent as ConstraintLayout).addView(emptyView, layoutParams)
                    }

                    is RelativeLayout -> {
                        RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                        ).apply {
                            addRule(RelativeLayout.CENTER_VERTICAL)
                        }

                        (parent as RelativeLayout).addView(emptyView, layoutParams)
                    }

                    is FrameLayout -> {
                        FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.WRAP_CONTENT
                        ).apply {
                            gravity = Gravity.CENTER
                        }

                        (parent as FrameLayout).addView(emptyView, layoutParams)
                    }

                    else -> {
                        ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )

                        (parent as ViewGroup).addView(emptyView, layoutParams)
                    }
                }

                initialized = true

                emptyView.gone(true)
            }
        }
    }

    private val emptyObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            adapter?.let { adapter ->
                emptyView.gone(adapter.itemCount != 0)
            }
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)

        adapter?.registerAdapterDataObserver(emptyObserver)
        emptyObserver.onChanged()
    }
}