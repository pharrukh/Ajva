package com.normuradov.ajva.extentions

fun String.trimToLengthWithEllipsis(maxLength: Int): String {
    return if (this.length <= maxLength) {
        this
    } else {
        this.substring(0, maxLength) + "..."
    }
}