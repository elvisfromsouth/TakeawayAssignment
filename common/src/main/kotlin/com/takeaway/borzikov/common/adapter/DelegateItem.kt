package com.takeaway.borzikov.common.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

/**
 * Base wrap-type for every delegate item.
 * Uses for contain all thing of delegate in single place.
 */
abstract class DelegateItem<T : ListItem> {
    abstract val delegate: AdapterDelegate<List<ListItem>>
    abstract val diffUtils: DiffUtil.ItemCallback<T>
    abstract val itemClass: Class<T>
}