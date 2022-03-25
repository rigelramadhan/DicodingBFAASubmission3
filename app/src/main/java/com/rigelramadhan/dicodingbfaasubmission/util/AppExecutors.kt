package com.rigelramadhan.dicodingbfaasubmission.util

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors {
    val diskIO: Executor = Executors.newSingleThreadExecutor()
    val networkIO: Executor = Executors.newFixedThreadPool(3)
}