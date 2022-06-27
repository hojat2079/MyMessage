package com.application.ebcom.util


sealed class Resource<T>(val message: String? = null, val data: T? = null) {
    class Success<T>(data: T?) : Resource<T>(data = data)
    class Loading<T>(data: T?=null) : Resource<T>(data = data)
    class Error<T>(data: T?=null, message: String?) : Resource<T>(message = message, data = data)
}