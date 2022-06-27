package com.application.ebcom.feature.message.presetation.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.application.ebcom.R
import com.application.ebcom.databinding.ViewAppbarBinding

class CustomAppbar(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    private lateinit var binding: ViewAppbarBinding
    private var backBtn: ImageView
    private var label: TextView

    var onBackButtonClickListener: OnClickListener? = null
        set(value) {
            field = value
            backBtn.setOnClickListener(onBackButtonClickListener)
        }

    init {
        inflate(context, R.layout.view_appbar, this)
        binding= ViewAppbarBinding.bind(this)

        backBtn = findViewById(R.id.backButton)
        label = findViewById(R.id.titleToolbarTextView)

        if (attrs != null) {
            val values = context.obtainStyledAttributes(attrs, R.styleable.CustomAppbar)
            val title = values.getString(R.styleable.CustomAppbar_appBarTitle)
            if (title != null && title.isNotEmpty())
                label.text = title
            values.recycle()
        }
    }
}