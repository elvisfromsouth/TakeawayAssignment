package com.takeaway.borzikov.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RestaurantsDecoration(
    private val horizontalMargin: Int,
    private val verticalMargin: Int,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val holder = parent.getChildViewHolder(view)
        val position = holder.layoutPosition.takeIf { it != RecyclerView.NO_POSITION }
            ?: holder.oldPosition

        outRect.set(
            horizontalMargin,
            if (position == 0) 0 else verticalMargin,
            horizontalMargin,
            0,
        )
    }

}