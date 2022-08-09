package com.takeaway.borzikov.tests

import com.takeaway.borzikov.R
import com.takeaway.borzikov.RestaurantListScreenState
import com.takeaway.borzikov.RestaurantListStateHandlerImpl
import com.takeaway.borzikov.common.Text
import com.takeaway.borzikov.common.adapter.ListItem
import io.mockk.mockk
import org.junit.Test

class RestaurantListStateHandlerImplTest {

    private val handler = RestaurantListStateHandlerImpl()

    @Test
    fun `WHEN request error state THEN return default error state`() {
        val targetResult = RestaurantListScreenState.Error(Text.Resource(R.string.default_error))

        val result = handler.getErrorState(NullPointerException())

        assert(targetResult == result)
    }

    @Test
    fun `WHEN request content state with empty list THEN return error state`() {
        val targetResult = RestaurantListScreenState.Error(Text.Resource(R.string.empty_list_title))

        val result = handler.getContentState(emptyList())

        assert(targetResult == result)
    }

    @Test
    fun `WHEN request content state with no empty list THEN return content state`() {
        val items: List<ListItem> = listOf(mockk())
        val targetResult = RestaurantListScreenState.Content(items)

        val result = handler.getContentState(items)

        assert(targetResult == result)
    }
}