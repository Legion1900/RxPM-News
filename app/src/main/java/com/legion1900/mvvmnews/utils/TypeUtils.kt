package com.legion1900.mvvmnews.utils

fun IntRange.toIntArray(): IntArray {
    val array = IntArray(this.count())
    val iterator = iterator()
    for (i in array.indices)
        array[i] = iterator.nextInt()
    return array
}