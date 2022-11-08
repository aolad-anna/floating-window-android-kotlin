package com.example.floatie.view.builder

import android.content.Context
import com.example.floatie.view.FloatieRemoveItem

interface IFloatieRemoveItemBuilder {
    fun with(context: Context): IFloatieRemoveItemBuilder
    fun setRemoveXDrawable(drawable: Int): IFloatieRemoveItemBuilder
    fun setShouldFollowDrag(shouldFollowDrag: Boolean): IFloatieRemoveItemBuilder /* Flag to determine if the remove view should animate and follow the dragged item */
    fun setExpandable(shouldExpand: Boolean): IFloatieRemoveItemBuilder /* Flag to determine if the remove view should expand to the size of the draggable item */
    fun build(): FloatieRemoveItem
}