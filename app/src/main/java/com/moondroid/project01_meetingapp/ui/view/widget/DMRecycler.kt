package com.moondroid.project01_meetingapp.ui.view.widget

import android.app.ActionBar
import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.utils.DMLog
import com.moondroid.project01_meetingapp.utils.view.gone
import com.moondroid.project01_meetingapp.utils.view.visible

class DMRecycler : RecyclerView {

    lateinit var emptyView: TextView

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
            text = "데이터가 존재하지 않습니다."
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.0f)
            setTextColor(ContextCompat.getColor(context, R.color.gray_dark01))
            gravity = Gravity.CENTER
            typeface = ResourcesCompat.getFont(context, R.font.nanum_square_round_eb)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        DMLog.e("[DMRecycler] , init() api call")

        parent?.let {
            if (parent is ViewGroup) {
                val layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                (parent as ViewGroup).addView(emptyView, layoutParams)
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