package it.paolomazza.newsapp.presentation

sealed class State<out T> {
    object LoadingState : State<Nothing>()
    data class ErrorState(var exception: Throwable) : State<Nothing>()
    data class Success<T>(var data: T) : State<T>()
}