package com.example.floatie

import android.graphics.Point
import android.view.*
import com.example.floatie.util.getScreenHeight
import com.uwuster.floatie.util.VelocityTrackerHelper
import com.example.floatie.util.getScreenWidth
import com.example.floatie.util.isViewGravityLeft
import com.example.floatie.util.isViewGravityTop
import kotlin.math.abs


/**
 * This makes the view draggable on a window
 */
class DraggableWindowItemCreator {

    private var listener: IFloatieDraggableWindowItemEventListener? = null
    private var velocityTrackerHelper: VelocityTrackerHelper = VelocityTrackerHelper()

    fun setViewAsDraggableItemOnWindow(view: View, windowManager: WindowManager, params: WindowManager.LayoutParams, listener: IFloatieDraggableWindowItemEventListener? = null, gravity: DraggableWindowItemGravity): DraggableWindowItemCreator {
        view.setOnTouchListener(DraggableViewTouchListener(windowManager, params, gravity))
        this.listener = listener

        return this
    }

    private inner class DraggableViewTouchListener(private val windowManager: WindowManager, private val params: WindowManager.LayoutParams, private val gravity: DraggableWindowItemGravity): View.OnTouchListener {
        // Track the initial position of the view in the window
        private var prevX: Int = params.x
        private var prevY: Int = params.y

        // Track the initial touch position
        private var prevTouchX: Float = -1F
        private var prevTouchY: Float = -1F

        private var deltaX: Float = -1F
        private var deltaY: Float = -1F

        private var lastAction: Int? = null

        override fun onTouch(view: View, event: MotionEvent): Boolean {
            val currentTouchPoint = Point(event.rawX.toInt(), event.rawY.toInt())

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    velocityTrackerHelper.setVelocityTracker(event)

                    // To make sure that the touch spot is correctly representing the position of the draggable view
                    alignTouchToViewCenter(view, currentTouchPoint)

                    prevX = params.x
                    prevY = params.y

                    prevTouchX = event.rawX
                    prevTouchY = event.rawY

                    lastAction = event.action
                    return true
                }
                MotionEvent.ACTION_UP -> {
                    if (lastAction == MotionEvent.ACTION_DOWN) {
                        listener?.onTouchEventChanged(view, Point(params.x, params.y), currentTouchPoint, velocityTrackerHelper.velocityX, velocityTrackerHelper.velocityY,
                            DraggableWindowItemTouchEvent.CLICK_EVENT)
                    } else {
                        listener?.onTouchEventChanged(view, Point(params.x, params.y), currentTouchPoint, velocityTrackerHelper.velocityX, velocityTrackerHelper.velocityY,
                            DraggableWindowItemTouchEvent.DRAG_STOP_EVENT)
                    }

                    lastAction = event.action
                    velocityTrackerHelper.recycleVelocityTracker()

                    return true
                }
                MotionEvent.ACTION_MOVE -> {
                    velocityTrackerHelper.calculateVelocity(event)

                    // Calculate motion difference between previous touch point and current touch point
                    deltaX = event.rawX - prevTouchX
                    deltaY = event.rawY - prevTouchY

                    // Calculate x based on initial gravity position or else it'll go the opposite direction.
                    // prevX holds the x position based on where it gravitates towards. If the view gravitates
                    // towards the left, we should add to the previous x position else subtract from previous x
                    // position because the view gravitates towards the right side
                    params.x = if (isViewGravityLeft(gravity)) prevX + deltaX.toInt() else prevX - deltaX.toInt()

                    // Calculate y based on initial gravity position or else it'll go the opposite direction.
                    // prevY holds the y position based on where it gravitates towards. If the view gravitates
                    // towards the top, we should add to the previous y position else subtract from previous y
                    // position because the view gravitates towards the bottom
                    params.y = if (isViewGravityTop(gravity)) prevY + deltaY.toInt() else prevY - deltaY.toInt()

                    windowManager.updateViewLayout(view, params)

                    listener?.onTouchEventChanged(view, Point(params.x, params.y), currentTouchPoint, velocityTrackerHelper.velocityX, velocityTrackerHelper.velocityY,
                        DraggableWindowItemTouchEvent.DRAG_EVENT)

                    lastAction = event.action
                    return true
                }
                else -> return false
            }
        }

        /**
         * Align the touch position to the view center based on gravity
         */
        fun alignTouchToViewCenter(view: View, currentTouchPoint: Point) {
            if (isViewGravityLeft(gravity)) {
                params.x = currentTouchPoint.x - view.width / 2
            } else {
                params.x = abs(currentTouchPoint.x - getScreenWidth(windowManager) + view.width / 2)
            }

            if (isViewGravityTop(gravity)) {
                params.y = currentTouchPoint.y - view.height
            } else {
                params.y = getScreenHeight(windowManager) - (currentTouchPoint.y + view.height / 2)
            }
            windowManager.updateViewLayout(view, params)
        }
    }
}

interface IFloatieDraggableWindowItemEventListener {
    fun onTouchEventChanged(
        view: View,
        currentViewPosition: Point,
        currentTouchPoint: Point,
        velocityX: Float,
        velocityY: Float,
        draggableWindowItemTouchEvent: DraggableWindowItemTouchEvent
    )
}

enum class DraggableWindowItemTouchEvent {
    CLICK_EVENT,
    DRAG_EVENT,
    DRAG_STOP_EVENT
}

/**
 * Enum class that represents the possible initial gravity placement of the draggable view when added to the window:
 *    TOP_LEFT: Gravitate towards the top-left corner
 *    TOP_RIGHT: Gravitate towards the top-right corner
 *    BOTTOM_LEFT: Gravitate towards the bottom-left corner
 *    BOTTOM_RIGHT: Gravitate towards the bottom-right corner
 */
enum class DraggableWindowItemGravity(val value: Int) {
    TOP_LEFT(Gravity.TOP or Gravity.LEFT),
    TOP_RIGHT(Gravity.TOP or Gravity.RIGHT),
    BOTTOM_LEFT(Gravity.BOTTOM or Gravity.LEFT),
    BOTTOM_RIGHT(Gravity.BOTTOM or Gravity.RIGHT),
    CENTER(Gravity.TOP or Gravity.CENTER)
}