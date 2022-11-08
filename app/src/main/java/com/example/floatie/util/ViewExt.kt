package com.example.floatie.util

import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.Rect
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.OvershootInterpolator

internal fun View.isInBounds(x: Int, y: Int): Boolean {
    val outRect = Rect()
    val location = IntArray(2)

    getDrawingRect(outRect)
    getLocationOnScreen(location)
    outRect.offset(location[0], location[1])

    return outRect.contains(x, y)
}

internal fun View.createResizeAnimator(scaleX: Float, scaleY: Float, duration: Long, listener: AnimatorListenerAdapter? = null): ViewPropertyAnimator {
    return animate()
        .scaleX(scaleX)
        .scaleY(scaleY)
        .setDuration(duration)
        .setListener(listener)
}

internal fun View.createMoveYAndResizeAnimator(destY: Float, scaleX: Float, scaleY: Float, duration: Long, updateListener: ValueAnimator.AnimatorUpdateListener? = null, listener: AnimatorListenerAdapter? = null): ViewPropertyAnimator {
    return animate()
        .translationY(destY)
        .scaleX(scaleX)
        .scaleY(scaleY)
        .setDuration(duration)
        .setUpdateListener(updateListener)
        .setListener(listener)
}

internal fun View.createFlingAnimator(duration: Long, tension: Float, updateListener: ValueAnimator.AnimatorUpdateListener, listener: AnimatorListenerAdapter? = null): ViewPropertyAnimator {
    return animate()
        .translationX(0F)
        .translationY(0F)
        .setDuration(duration)
        .setInterpolator(OvershootInterpolator(tension))
        .setListener(listener)
        .setUpdateListener(updateListener)
}

internal fun View.vibrate() {
    performHapticFeedback(HapticFeedbackConstants.CONFIRM)
}