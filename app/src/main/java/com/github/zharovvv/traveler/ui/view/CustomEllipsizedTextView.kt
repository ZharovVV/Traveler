package com.github.zharovvv.traveler.ui.view

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.ViewTreeObserver

class CustomEllipsizedTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                private val textView = this@CustomEllipsizedTextView
                override fun onGlobalLayout() {
                    textView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    val visibleLines: Int =
                        (textView.height - (textView.paddingTop + textView.paddingBottom)) / textView.lineHeight
                    textView.maxLines = visibleLines
                    textView.ellipsize = TextUtils.TruncateAt.END
                }
            }
        )
    }
}