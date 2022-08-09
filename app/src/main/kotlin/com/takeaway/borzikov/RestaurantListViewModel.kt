package com.takeaway.borzikov

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takeaway.borzikov.common.AppDispatchers
import com.takeaway.borzikov.common.SingleLiveEvent
import com.takeaway.borzikov.common.Text
import com.takeaway.borzikov.domain.RestaurantInteractor
import com.takeaway.borzikov.models.RestaurantSort
import com.takeaway.borzikov.models.SortingDialogDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RestaurantListViewModel @Inject constructor(
    private val interactor: RestaurantInteractor,
    private val screenStateHandler: RestaurantListStateHandler,
    private val dispatchers: AppDispatchers,
    private val restaurantItemsProvider: RestaurantItemsProvider,
) : ViewModel() {

    private val _sortingTitle = MutableLiveData<Text>(
        Text.Resource(restaurantItemsProvider.currentSorting.title)
    )
    val sortingTitle: LiveData<Text> = _sortingTitle

    private val _showSortingDialogDetails = SingleLiveEvent<SortingDialogDetails>()
    val showSortingDialogDetails: LiveData<SortingDialogDetails> = _showSortingDialogDetails

    private val _state =
        MutableLiveData<RestaurantListScreenState>(RestaurantListScreenState.Loading)
    val state: LiveData<RestaurantListScreenState> = _state

    private val sortingTypes = RestaurantSort.values()
    private val sortingTitles = sortingTypes.map { Text.Resource(it.title) }
    private var currentSortPosition: Int =
        sortingTypes.indexOf(restaurantItemsProvider.currentSorting)

    init {
        prepareContent()
    }

    fun onAskChangeSortingType() {
        _showSortingDialogDetails.value = SortingDialogDetails(
            Text.Resource(R.string.choosing_sorting_dialog_title),
            sortingTitles,
            currentSortPosition
        )
    }

    fun applyFilter(text: String) {
        val items = restaurantItemsProvider.getRestaurantItems(filter = text)
        _state.value = screenStateHandler.getContentState(items)
    }

    fun applySortingBySortPosition(position: Int) {
        if (currentSortPosition == position) return
        val newSorting = sortingTypes.getOrNull(position) ?: return
        currentSortPosition = position

        val items = restaurantItemsProvider.getRestaurantItems(newSorting)
        _state.value = screenStateHandler.getContentState(items)
        _sortingTitle.value = Text.Resource(newSorting.title)
    }

    private fun prepareContent() {
        viewModelScope.launch {
            val restaurantModels = try {
                withContext(dispatchers.io()) {
                    interactor.getRestaurants()
                }
            } catch (e: Exception) {
                _state.value = screenStateHandler.getErrorState(e)
                Log.e(TAG, e.message, e)
                return@launch
            }

            restaurantItemsProvider.setup(restaurantModels)

            val restaurantItems = restaurantItemsProvider.getRestaurantItems()
            _state.value = screenStateHandler.getContentState(restaurantItems)
        }
    }

    companion object {
        private const val TAG = "RestaurantListViewModel"
    }
}