package com.takeaway.borzikov.adapter

import androidx.recyclerview.widget.DiffUtil

class RestaurantDiffUtils : DiffUtil.ItemCallback<RestaurantListItem>() {

    override fun areItemsTheSame(
        oldItem: RestaurantListItem,
        newItem: RestaurantListItem
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: RestaurantListItem,
        newItem: RestaurantListItem
    ): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(
        oldItem: RestaurantListItem,
        newItem: RestaurantListItem
    ): Any? {
        if (oldItem.sortingDescription != newItem.sortingDescription) {
            return newItem.sortingDescription
        }
        return super.getChangePayload(oldItem, newItem)
    }

}