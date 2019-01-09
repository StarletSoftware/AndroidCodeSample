package starlet.tech.androidcodesample.model

import starlet.tech.androidcodesample.utils.Utils

data class MainInfoModel(
    val temp: Double,
    val pressure: Double,
    val humidity: Double,
    val temp_min: Double,
    val temp_max: Double
) {
    fun getCurrentTempInCelcius() = Utils.kelvinToCelsius(temp)

    fun getMaxTempInCelcius() = Utils.kelvinToCelsius(temp_max)

    fun getMinTempInCelcius() = Utils.kelvinToCelsius(temp_min)
}