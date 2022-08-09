package com.takeaway.borzikov

import com.takeaway.borzikov.domain.models.RestaurantModel
import com.takeaway.borzikov.domain.models.RestaurantStatus

val restaurantStatusComparator = compareBy<RestaurantModel> {
    when (it.status) {
        RestaurantStatus.OPEN -> 1
        RestaurantStatus.ORDER_AHEAD -> 2
        RestaurantStatus.CLOSE -> 3
    }
}