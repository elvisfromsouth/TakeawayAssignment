package com.takeaway.borzikov.delegates

import androidx.lifecycle.SavedStateHandle
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class StringSavedState(
    private val savedState: SavedStateHandle,
    private val key: String,
) : ReadWriteProperty<Any, String> {

    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return savedState.get<String>(key).orEmpty()
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        savedState[key] = value
    }

}