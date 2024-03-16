package com.example.skycast.utility

sealed class Status {
    class Success(var data: Any): Status(){
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Success) return false
            return data == other.data
        }

        override fun hashCode(): Int {
            return data.hashCode()
        }
    }
    class Fail(var msg:Throwable): Status()
    object Loading: Status()
}