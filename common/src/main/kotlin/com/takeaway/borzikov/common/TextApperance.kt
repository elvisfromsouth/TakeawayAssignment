package com.takeaway.borzikov.common

import android.content.Context
import androidx.annotation.StringRes

/**
 * Extracting text from resources without depends on Context.
 */
sealed class Text {

    abstract fun extract(context: Context): String

    data class Simple(val text: String) : Text() {
        override fun extract(context: Context): String {
            return text
        }
    }

    data class Resource(@StringRes val resId: Int) : Text() {
        override fun extract(context: Context): String {
            return context.getString(resId)
        }
    }

    data class ResourceWithArguments(
        @StringRes val resId: Int,
        private val arguments: List<Any>
    ) : Text() {

        constructor(
            @StringRes resId: Int,
            vararg arguments: Any
        ) : this(resId, arguments.toList())

        override fun extract(context: Context): String {
            val arguments = arguments.map { if (it is Text) it.extract(context) else it }
            return context.getString(resId, *arguments.toTypedArray())
        }
    }


}