package com.takeaway.borzikov.mappers

import com.takeaway.borzikov.R
import com.takeaway.borzikov.adapter.RestaurantListItem
import com.takeaway.borzikov.common.Text
import com.takeaway.borzikov.domain.models.RestaurantModel
import com.takeaway.borzikov.domain.models.RestaurantStatus
import com.takeaway.borzikov.models.RestaurantSort
import javax.inject.Inject

class RestaurantItemMapper @Inject constructor(
) : (RestaurantModel, RestaurantSort) -> RestaurantListItem {

    override fun invoke(model: RestaurantModel, sorting: RestaurantSort): RestaurantListItem {
        val formattedValue = sorting.value.invoke(model).toString()

        return RestaurantListItem(
            name = model.name,
            status = model.status.getTitle(),
            sortingDescription = Text.ResourceWithArguments(
                R.string.sorting_value_pattern,
                Text.Resource(sorting.title),
                formattedValue
            )
        )
    }

    private fun RestaurantStatus.getTitle(): Text.Resource {
        val resId = when (this) {
            RestaurantStatus.OPEN -> R.string.restaurant_open
            RestaurantStatus.ORDER_AHEAD -> R.string.restaurant_order_ahead
            RestaurantStatus.CLOSE -> R.string.restaurant_closed
        }

        return Text.Resource(resId)
    }
}