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
import com.takeaway.borzikov.models.RestaurantSort
import com.takeaway.borzikov.models.SortingDialogDetails
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RestaurantListViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @MockK
    lateinit var interactor: RestaurantInteractor

    @MockK
    lateinit var screenStateHandler: RestaurantListStateHandler

    @RelaxedMockK
    lateinit var restaurantItemsProvider: RestaurantItemsProvider

    private val dispatchers = TestAppDispatchers()

    lateinit var viewModel: RestaurantListViewModel


    @Before
    fun init() {
        every { restaurantItemsProvider.currentSorting } returns RestaurantSort.BEST
        coEvery { interactor.getRestaurants() } returns listOf()
        every { restaurantItemsProvider.getRestaurantItems() } returns listOf()
        every { screenStateHandler.getContentState(any()) } returns RestaurantListScreenState.Loading

        viewModel = RestaurantListViewModel(
            interactor = interactor,
            screenStateHandler = screenStateHandler,
            dispatchers = dispatchers,
            restaurantItemsProvider = restaurantItemsProvider,
        )
    }


    @Test
    fun `WHEN asks change sort type THEN send dialog data`() {
        viewModel.applySortingBySortPosition(2)
        testAskChangeSortingType(2)
    }

    @Test
    fun `WHEN applies wrong sort position THEN keep using previous sorting`() {
        viewModel.applySortingBySortPosition(3)
        viewModel.applySortingBySortPosition(-400)
        testAskChangeSortingType(3)
    }

    @Test
    fun `WHEN applies same sort position THEN sorting applies successfully`() {
        viewModel.applySortingBySortPosition(4)
        viewModel.applySortingBySortPosition(4)
        testAskChangeSortingType(4)
    }


    @Test
    fun `WHEN filter list THEN filter applies successfully`() {
        val filterResult = listOf<RestaurantListItem>()
        val resultState = RestaurantListScreenState.Loading
        every { restaurantItemsProvider.getRestaurantItems(any(), any()) } returns filterResult
        every { screenStateHandler.getContentState(any()) } returns resultState

        viewModel.applyFilter("some text")

        verify {
            restaurantItemsProvider.getRestaurantItems(filter = "some text")
        }

        viewModel.state.assert(resultState)
    }

    private fun testAskChangeSortingType(targetSelectedPosition: Int) {
        val targetResult = SortingDialogDetails(
            title = Text.Resource(R.string.choosing_sorting_dialog_title),
            selectedPosition = targetSelectedPosition,
            items = listOf(
                R.string.sorting_best,
                R.string.sorting_new,
                R.string.sorting_rating,
                R.string.sorting_distance,
                R.string.sorting_popularity,
                R.string.sorting_average_product_price,
                R.string.sorting_delivery_cost,
                R.string.sorting_min_cost,
            ).map { Text.Resource(it) },
        )
        viewModel.onAskChangeSortingType()

        viewModel.showSortingDialogDetails.assert(targetResult)
    }


}