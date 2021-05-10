package com.github.zharovvv.traveler.utils

import android.content.res.Resources

fun Float.pxToDp(): Float {
    // dp / px = device density / default density (160 dpi)
    val density = Resources.getSystem().displayMetrics.density
    return this / density
}

fun Float.dpToPx(): Float {
    return this * Resources.getSystem().displayMetrics.density
}

fun Int.pxToDp(): Int {
    val density = Resources.getSystem().displayMetrics.density
    return (this / density).toInt()
}

fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}