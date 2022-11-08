package com.example.floatie.view.builder

import android.view.View
import com.example.floatie.DraggableWindowItemGravity
import com.example.floatie.IFloatieDraggableWindowItemEventListener
import com.example.floatie.view.FloatieDraggableItem

interface IFloatieDraggableItemBuilder {
    fun setLayout(layout: View): IFloatieDraggableItemBuilder
    fun setGravity(gravity: DraggableWindowItemGravity): IFloatieDraggableItemBuilder // The starting placement value for the draggable item
    fun setStartingXPosition(pos: Int): IFloatieDraggableItemBuilder // The horizontal starting position value of the draggable item on the x-axis
    fun setStartingYPosition(pos: Int): IFloatieDraggableItemBuilder // The vertical starting position value of the draggable item on the y-axis
    fun setListener(listener: IFloatieDraggableWindowItemEventListener): IFloatieDraggableItemBuilder
    fun build(): FloatieDraggableItem
}