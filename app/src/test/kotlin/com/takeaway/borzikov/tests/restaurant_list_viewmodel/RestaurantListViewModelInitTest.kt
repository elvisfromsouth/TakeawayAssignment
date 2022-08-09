package com.takeaway.borzikov.tests.restaurant_list_viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.takeaway.borzikov.MainDispatcherRule
import com.takeaway.borzikov.R
import com.takeaway.borzikov.RestaurantItemsProvider
import com.takeaway.borzikov.RestaurantListScreenState
import com.takeaway.borzikov.RestaurantListStateHandler
import com.takeaway.borzikov.RestaurantListViewModel
import com.takeaway.borzikov.TestAppDispatchers
import com.takeaway.borzikov.adapter.RestaurantListItem
import com.takeaway.borzikov.assert
import com.takeaway.borzikov.common.Text
import com.takeaway.borzikov.domain.RestaurantInteractor
import com.takeaway.borzikov.domain.models.RestaurantModel
import com.takeaway.borzikov.models.RestaurantSort
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class RestaurantListViewModelInitTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var interactor: RestaurantInteractor

    @RelaxedMockK
    lateinit var screenStateHandler: RestaurantListStateHandler

    @RelaxedMockK
    lateinit var restaurantItemsProvider: RestaurantItemsProvider

    private val dispatchers = TestAppDispatchers()

    lateinit var viewModel: RestaurantListViewModel


    private fun initViewModel() {
        viewModel = RestaurantListViewModel(
            interactor = interactor,
            screenStateHandler = screenStateHandler,
            restaurantItemsProvider = restaurantItemsProvider,
            dispatchers = dispatchers
        )
    }

    @Test
    fun `WHEN viewmodel init THEN sorting title init from provider`() {
        every { restaurantItemsProvider.currentSorting } returns RestaurantSort.DISTANCE

        initViewModel()

        viewModel.sortingTitle.observeForever {
            assert(it == Text.Resource(R.string.sorting_distance))
        }
    }

    @Test
    fun `WHEN restaurant successfully requested THEN setup restaurantItemsProvider`() {
        val result = listOf<RestaurantModel>()
        every { restaurantItemsProvider.currentSorting } returns RestaurantSort.DISTANCE
        coEvery { interactor.getRestaurants() } returns result

        initViewModel()

        verify {
            restaurantItemsProvider.setup(result)
        }
    }

    @Test
    fun `WHEN restaurant unsuccessfully requested THEN show error state`() {
        val state = RestaurantListScreenState.Loading
        coEvery { interactor.getRestaurants() } throws Exception()
        every { screenStateHandler.getErrorState(any()) } returns state
        every { restaurantItemsProvider.currentSorting } returns RestaurantSort.DISTANCE

        initViewModel()

        viewModel.state.assert(state)
    }

    @Test
    fun `WHEN restaurant successfully requested THEN show items`() {
        val restaurantModels = listOf<RestaurantModel>()
        val restaurantItems = listOf<RestaurantListItem>()
        val resultState = RestaurantListScreenState.Loading

        coEvery { interactor.getRestaurants() } returns restaurantModels
        every { restaurantItemsProvider.getRestaurantItems() } returns restaurantItems
        every { screenStateHandler.getContentState(restaurantItems) } returns resultState
        every { restaurantItemsProvider.currentSorting } returns RestaurantSort.DISTANCE

        initViewModel()

        viewModel.state.assert(resultState)
    }

}