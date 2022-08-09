package com.takeaway.borzikov

import com.takeaway.borzikov.common.Text
import com.takeaway.borzikov.common.adapter.ListItem
import javax.inject.Inject

interface RestaurantListStateHandler {

    fun getErrorState(exception: Exception): RestaurantListScreenState
    fun getContentState(items: List<ListItem>): RestaurantListScreenState
}

class RestaurantListStateHandlerImpl @Inject constructor() : RestaurantListStateHandler {

    override fun getErrorState(exception: Exception): RestaurantListScreenState {
        return RestaurantListScreenState.Error(Text.Resource(R.string.default_error))
    }

    override fun getContentState(items: List<ListItem>): RestaurantListScreenState {
        return if (items.isEmpty()) {
            RestaurantListScreenState.Error(Text.Resource(R.string.empty_list_title))
        } else {
            RestaurantListScreenState.Content(items)
        }
    }
}