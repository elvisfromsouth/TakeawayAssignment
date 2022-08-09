package com.takeaway.borzikov.common.adapter

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

/**
 * General RecyclerView Adapter for DelegateItems.
 */
class DelegateAdapter(
    vararg delegates: DelegateItem<*>
) : AsyncListDifferDelegationAdapter<ListItem>(
    @Suppress("UNCHECKED_CAST")
    ListItemItemCallback(delegates.toList() as List<DelegateItem<ListItem>>),
) {

    init {
        delegates.forEach {
            delegatesManager.addDelegate(it.delegate)
        }
    }
}