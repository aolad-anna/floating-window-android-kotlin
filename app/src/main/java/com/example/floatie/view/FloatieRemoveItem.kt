package com.example.floatie.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.example.bubbletoffee.R
import com.example.floatie.util.REMOVE_ITEM_NO_CONTEXT
import com.example.floatie.view.builder.IFloatieRemoveItemBuilder
import java.lang.IllegalStateException

/**
 * FloatieRemoveItem: The item that represents the destination to drag towards when we wish to stop the service
 */
class FloatieRemoveItem(builder: Builder) {
    val removeLayout: View =
        LayoutInflater.from(builder.context).inflate(R.layout.floatie_remove_layout, null)

    val removeCircleView: ImageView = removeLayout.findViewById(R.id.remove_circle)
    val removeXView: ImageView = removeLayout.findViewById(R.id.remove_x)

    val expandable = builder.expandable
    val shouldFollowDrag = builder.shouldFollowDrag

    init {
        // Replacing the default X remove view with the passed in drawable
        builder.drawable?.let { updateRemoveXDrawable(builder.context, it) }

        setVisible(false)
    }

    internal fun setVisible(shouldShow: Boolean) {
        removeCircleView.visibility = if (shouldShow) View.VISIBLE else View.GONE
        removeXView.visibility = if (shouldShow) View.VISIBLE else View.GONE
    }

    fun updateRemoveXDrawable(context: Context, drawableId: Int) {
        val drawable = ResourcesCompat.getDrawable(context.resources, drawableId, context.theme)
        val removeXView = removeLayout.findViewById<ImageView>(R.id.remove_x)
        removeXView.setImageDrawable(drawable)
    }

    class Builder: IFloatieRemoveItemBuilder {
        lateinit var context: Context

        var drawable: Int? = null
        var shouldFollowDrag = false
        var expandable = false

        override fun with(context: Context) = this.apply { this.context = context }

        override fun setRemoveXDrawable(drawable: Int) = this.apply { this.drawable = drawable }

        override fun setShouldFollowDrag(shouldFollowDrag: Boolean) = this.apply { this.shouldFollowDrag }

        override fun setExpandable(shouldExpand: Boolean) = this.apply { this.expandable = shouldExpand }

        override fun build(): FloatieRemoveItem {
            if (!this::context.isInitialized) throw IllegalStateException(REMOVE_ITEM_NO_CONTEXT)

            return FloatieRemoveItem(this)
        }
    }
}