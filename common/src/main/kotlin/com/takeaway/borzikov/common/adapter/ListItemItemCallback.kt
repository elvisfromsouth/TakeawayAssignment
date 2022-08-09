package com.takeaway.borzikov.common.adapter

import androidx.recyclerview.widget.DiffUtil

class ListItemItemCallback(
    delegates: List<DelegateItem<ListItem>>
) : DiffUtil.ItemCallback<ListItem>() {

    private val delegatesDictionary = delegates.associate { it.itemClass to it.diffUtils }

    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return delegatesDictionary[oldItem.javaClass]?.areItemsTheSame(oldItem, newItem)
            ?: throw IllegalStateException("DiffUtils not found for ${oldItem.javaClass}.")
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return delegatesDictionary[oldItem.javaClass]?.areContentsTheSame(oldItem, newItem)
            ?: throw IllegalStateException("DiffUtils not found for ${oldItem.javaClass}.")
    }

    override fun getChangePayload(oldItem: ListItem, newItem: ListItem): Any? {
        return delegatesDictionary[oldItem.javaClass]?.getChangePayload(oldItem, newItem)
    }
}