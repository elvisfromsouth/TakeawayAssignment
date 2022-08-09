package com.takeaway.borzikov

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.takeaway.borzikov.adapter.RestaurantDelegateItem
import com.takeaway.borzikov.adapter.RestaurantsDecoration
import com.takeaway.borzikov.common.adapter.DelegateAdapter
import com.takeaway.borzikov.databinding.ActivityRestaurantListBinding
import com.takeaway.borzikov.models.SortingDialogDetails
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(FlowPreview::class)
@AndroidEntryPoint
class RestaurantListActivity : AppCompatActivity() {

    private val viewModel: RestaurantListViewModel by viewModels()

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityRestaurantListBinding.inflate(layoutInflater)
    }

    private val adapter = DelegateAdapter(
        RestaurantDelegateItem()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupUi()
        observeValues()
    }

    private fun setupUi() {
        with(binding) {
            rvRestaurants.adapter = adapter
            rvRestaurants.layoutManager = LinearLayoutManager(this@RestaurantListActivity)
            rvRestaurants.addItemDecoration(
                RestaurantsDecoration(
                    horizontalMargin = resources.getDimensionPixelOffset(R.dimen.restaurant_horizontal_margin),
                    verticalMargin = resources.getDimensionPixelOffset(R.dimen.restaurant_vertical_margin),
                )
            )

            etFilter.textChanges()
                .debounce(DEBOUNCE_INPUT)
                .onEach { viewModel.applyFilter(it?.toString().orEmpty()) }
                .launchIn(lifecycleScope)

            btnSortingType.setOnClickListener {
                viewModel.onAskChangeSortingType()
            }
        }
    }

    private fun observeValues() {
        viewModel.sortingTitle.observe(this) {
            binding.btnSortingType.text = it.extract(this)
        }

        viewModel.state.observe(this) {
            updateVisibility(it)
            when (it) {
                is RestaurantListScreenState.Content -> adapter.items = it.items
                is RestaurantListScreenState.Error -> binding.tvErrorMessage.text =
                    it.text.extract(this)
                RestaurantListScreenState.Loading -> Unit
            }
        }

        viewModel.showSortingDialogDetails.observe(this) {
            showSortingTypeChoosingDialog(it)
        }
    }

    private fun updateVisibility(state: RestaurantListScreenState) {
        with(binding) {
            rvRestaurants.isVisible = state is RestaurantListScreenState.Content
            progressBar.isVisible = state is RestaurantListScreenState.Loading
            tvErrorMessage.isVisible = state is RestaurantListScreenState.Error
        }
    }

    private fun showSortingTypeChoosingDialog(details: SortingDialogDetails) {
        AlertDialog.Builder(this)
            .setTitle(details.title.extract(this))
            .setSingleChoiceItems(
                details.items.map { it.extract(this) }.toTypedArray(),
                details.selectedPosition
            ) { dialog, position ->
                dialog.dismiss()
                viewModel.applySortingBySortPosition(position)
            }
            .create()
            .show()
    }

    companion object {
        private const val DEBOUNCE_INPUT = 300L
    }
}