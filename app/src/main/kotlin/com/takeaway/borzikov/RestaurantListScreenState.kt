package com.takeaway.borzikov

import com.takeaway.borzikov.common.Text
import com.takeaway.borzikov.common.adapter.ListItem

sealed class RestaurantListScreenState {
    object Loading : RestaurantListScreenState()
    data class Error(val text: Text) : RestaurantListScreenState()
    data class Content(val items: List<ListItem>) : RestaurantListScreenState()
}