package com.hfad.mynews.utils

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String?) : Resource<Nothing>()
    class Loading<T> : Resource<T>()
}
