package com.example.composeWeatherApp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import javax.annotation.concurrent.Immutable

@Immutable
@Parcelize
data class SearchModel(val list: List<WeatherModel> = listOf(WeatherModel()), val state: ResponseState = ResponseState.Initial): Parcelable

@Parcelize
sealed class ResponseState : Parcelable {
    data object Initial: ResponseState()
    data object Success: ResponseState()
    data class Failure(val error: String): ResponseState()
}
