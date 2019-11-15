package com.legion1900.mvvmnews.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch

val <T> LiveData<T>.blockingValue: T
    get() {
        var value: T? = null
        val latch = CountDownLatch(1)
        val observer = Observer<T> {
            value = it
            latch.countDown()
        }
        observeForever(observer)
        latch.await()
        removeObserver(observer)
        return value!!
    }