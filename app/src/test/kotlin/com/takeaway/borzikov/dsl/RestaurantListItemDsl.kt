package com.takeaway.borzikov.dsl

import com.takeaway.borzikov.adapter.RestaurantListItem
import com.takeaway.borzikov.common.Text
import com.takeaway.borzikov.common.testing.randomString
import com.takeaway.borzikov.common.testing.randomText

fun createRestaurantListItem(init: RestaurantListItemDsl.() -> Unit): RestaurantListItem {
    val dsl = RestaurantListItemDsl()
    dsl.init()
    return dsl.toOriginal()
}

class RestaurantListItemDsl {
    var name: String = randomString()
    var sortingDescription: Text = randomText()
    var status: Text = randomText()

    fun toOriginal(): RestaurantListItem {
        return RestaurantListItem(
            name = name,
            sortingDescription = sortingDescription,
            status = status,
        )
    }
}