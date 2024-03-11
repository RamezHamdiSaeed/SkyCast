package com.example.skycast.utility

import com.example.skycast.model.Weather

sealed class Status {
    class Success(var weather: Weather): Status()
    class Fail(var msg:Throwable): Status()
    object Loading: Status()
}