package com.takeaway.borzikov

import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart


fun EditText.textChanges(): Flow<CharSequence?> {
    return callbackFlow {
        val listener = addTextChangedListener { trySend(it) }
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }
}