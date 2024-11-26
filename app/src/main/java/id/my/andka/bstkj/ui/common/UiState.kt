package id.my.andka.bstkj.ui.common

sealed class UiState<T>(
    val data: T? = null,
    val message: String = "",
) {
    class Success<T>(data: T) : UiState<T>(data)
    class Loading<T> : UiState<T>()
    class Idle<T> : UiState<T>()
    class Error<T>(message: String, data: T? = null) : UiState<T>(data, message)
}