package com.example.skycast.utility

sealed class Status {
    class Success(var data: Any): Status()
    class Fail(var msg:Throwable): Status()
    object Loading: Status()
}