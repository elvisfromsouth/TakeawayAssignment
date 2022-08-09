package com.takeaway.borzikov.adapter

import com.takeaway.borzikov.common.Text
import com.takeaway.borzikov.common.adapter.ListItem

data class RestaurantListItem(
    val name: String,
    val sortingDescription: Text,
    val status: Text,
) : ListItem
