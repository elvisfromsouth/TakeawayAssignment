package com.takeaway.borzikov.models

import com.takeaway.borzikov.common.Text

data class SortingDialogDetails(
    val title: Text,
    val items: List<Text>,
    val selectedPosition: Int
)