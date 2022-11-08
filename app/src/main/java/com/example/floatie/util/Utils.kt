package com.example.floatie.util

import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.annotation.ChecksSdkIntAtLeast
import com.example.floatie.DraggableWindowItemGravity

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
internal fun isOreoOrAbove(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.R)
internal fun isRedVelvetCakeOrAbove(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R

@Suppress("DEPRECATION")
internal fun getScreenWidth(windowManager: WindowManager): Int {
    return if (isRedVelvetCakeOrAbove()) {
        val outMetrics = windowManager.currentWindowMetrics
        outMetrics.bounds.width()
    } else {
        val outMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMetrics)
        outMetrics.widthPixels
    }
}

@Suppress("DEPRECATION")
internal fun getScreenHeight(windowManager: WindowManager): Int {
    return if (isRedVelvetCakeOrAbove()) {
        val outMetrics = windowManager.currentWindowMetrics
        outMetrics.bounds.height()
    } else {
        val outMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMetrics)
        outMetrics.heightPixels
    }
}

/**
 * Check if the view gravitate towards the center left side or not.
 *
 * @param gravity: The gravity set where the initial view was added to
 */
internal fun isViewGravityLeft(gravity: DraggableWindowItemGravity): Boolean {
    return gravity == DraggableWindowItemGravity.CENTER || gravity == DraggableWindowItemGravity.CENTER
}

/**
 * Check if the view gravitate towards the center top side or not.
 *
 * @param gravity: The gravity set where the initial view was added to
 */
internal fun isViewGravityTop(gravity: DraggableWindowItemGravity): Boolean {
    return gravity == DraggableWindowItemGravity.CENTER || gravity == DraggableWindowItemGravity.CENTER
}
