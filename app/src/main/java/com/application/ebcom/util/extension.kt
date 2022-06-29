package com.application.ebcom.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.application.ebcom.R
import com.google.android.material.snackbar.Snackbar
import java.lang.IllegalStateException

fun String.toFaNumber(): String {
    var result = ""
    var fa: Char
    for (ch in this) {
        fa = ch
        when (ch) {
            '0' -> fa = '۰'
            '1' -> fa = '۱'
            '2' -> fa = '۲'
            '3' -> fa = '۳'
            '4' -> fa = '۴'
            '5' -> fa = '۵'
            '6' -> fa = '۶'
            '7' -> fa = '۷'
            '8' -> fa = '۸'
            '9' -> fa = '۹'
        }
        result = "${result}$fa"
    }
    return result
}


fun Fragment.showLoading(mustShow:Boolean) {
    val rootView: CoordinatorLayout = requireView() as CoordinatorLayout
    val viewContext: Context = requireContext()
    rootView.let {
        viewContext.let { context ->
            var loadingView = it.findViewById<View>(R.id.baseViewLoading)
            if (loadingView == null && mustShow) {
                loadingView =
                    LayoutInflater.from(context).inflate(R.layout.view_loading, it, false)
                it.addView(loadingView)
            }
            loadingView?.visibility = if (mustShow) View.VISIBLE else View.GONE
        }
    }
}

fun Fragment.showEmptyState(layoutResId: Int): View? {
    val rootView: CoordinatorLayout = requireView() as CoordinatorLayout
    val viewContext: Context = requireContext()
    rootView.let {
        viewContext.let { context ->
            var emptyState = it.findViewById<View>(R.id.EmptyState)
            if (emptyState == null) {
                emptyState = LayoutInflater.from(context).inflate(layoutResId, it, false)
                it.addView(emptyState)
            }
            emptyState.visibility = View.VISIBLE
            return emptyState
        }
    }
    return null
}

fun Fragment.showSnackBar(
    message: String,
    duration: Int = Snackbar.LENGTH_SHORT,
    action: View.OnClickListener? = null,
    actionTitle: String = ""
) {
    val rootView: CoordinatorLayout = requireView() as CoordinatorLayout
    val viewContext: Context = requireContext()
    rootView.let {
        Snackbar.make(it, message, duration).apply {
            setBackgroundTint(ContextCompat.getColor(viewContext, R.color.blue))
            view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                .apply {
                    textSize = 14f
                    setTextColor(ContextCompat.getColor(viewContext, R.color.white))
                }
            if (action != null && actionTitle.isNotEmpty()) {
                setAction(actionTitle, action)
                setActionTextColor(ContextCompat.getColor(viewContext, R.color.white))
            }
            show()
        }

    }
}

fun Fragment.showError(message:String=getString(R.string.error_message_connection)) {
    val rootView: CoordinatorLayout = requireView() as CoordinatorLayout
    val viewContext: Context = requireContext()
    viewContext.let {
         showSnackBar(
                message
            )
        }
    }

fun Fragment.shareMessage(message: String, subject: String){
    val intent = Intent(android.content.Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    intent.putExtra(Intent.EXTRA_TEXT, message)
    startActivity(Intent.createChooser(intent, subject))
}

fun Activity.convertDpToPixel(dp: Float, context: Context?): Float {
    return if (context != null) {
        val resources = context.resources
        val metrics = resources.displayMetrics
        dp * (metrics.densityDpi.toFloat()) / DisplayMetrics.DENSITY_DEFAULT
    } else {
        val metrics = Resources.getSystem().displayMetrics
        dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}

