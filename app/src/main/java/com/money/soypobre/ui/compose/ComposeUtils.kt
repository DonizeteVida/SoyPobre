package com.money.soypobre.ui.compose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State

inline fun <T : Any> MutableState<T>.update(
    crossinline predicate: (T) -> Boolean = { true }
): (T) -> Unit = {
    if (predicate(it)) {
        this.value = it
    }
}

inline fun <T : Any, R : Any> State<T>.map(
    block: T.() -> R
) = value.run(block)