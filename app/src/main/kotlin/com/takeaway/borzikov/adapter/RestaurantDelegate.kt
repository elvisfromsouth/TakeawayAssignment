package com.takeaway.borzikov.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.takeaway.borzikov.common.Text
import com.takeaway.borzikov.common.adapter.DelegateItem
import com.takeaway.borzikov.common.adapter.ListItem
import com.takeaway.borzikov.databinding.RestaurantItemBinding

class RestaurantDelegateItem : DelegateItem<RestaurantListItem>() {

    override val diffUtils = RestaurantDiffUtils()

    override val itemClass = RestaurantListItem::class.java

    override val delegate: AdapterDelegate<List<ListItem>> =
        adapterDelegateViewBinding<RestaurantListItem, ListItem, RestaurantItemBinding>(
            viewBinding = { layoutInflater, root ->
                RestaurantItemBinding.inflate(layoutInflater, root, false)
            },
        ) {
            bind { payloads ->
                with(binding) {
                    val sortingPayload = payloads.lastOrNull() as? Text
                    if (sortingPayload != null) {
                        tvSortingDescription.text = sortingPayload.extract(context)
                    } else {
                        tvName.text = item.name
                        tvStatus.text = item.status.extract(context)
                        tvSortingDescription.text = item.sortingDescription.extract(context)
                    }
                }
            }
        }
}
