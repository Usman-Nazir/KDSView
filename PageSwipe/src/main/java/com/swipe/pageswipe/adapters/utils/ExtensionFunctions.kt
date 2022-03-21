package com.swipe.pageswipe.adapters.utils

public fun String?.ifNullEmpty(): String {
    if (this ==null)
        return ""
    else return this
}

public fun String?.ifNullEmptyy(): String {
    if (this ==null)
        return ""
    else return this
}

fun Long?.ifOneAddZero(): String {
    if (this.toString().length ==1)
        return "0"+this.toString()
    else return this.toString()
}

fun Int?.ifOneAddZero(): String {
    if (this.toString().length ==1)
        return "0"+this.toString()
    else return this.toString()
}
class ExtensionFunctions {
}