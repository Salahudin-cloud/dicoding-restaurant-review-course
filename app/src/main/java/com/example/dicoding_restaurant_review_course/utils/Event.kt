package com.example.dicoding_restaurant_review_course.utils

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled){
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent() : T = content

}