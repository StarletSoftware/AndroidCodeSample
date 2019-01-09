package starlet.tech.androidcodesample.model

data class CityWeatherModel(
    val coord: Coord,
    val weather: List<WeatherModel>,
    val base: String,
    val main: MainInfoModel,
    val visibility: Int,
    val wind: WindModel,
    val clouds: CloudsModel,
    val dt: Long,
    val sys: SysModel,
    val id: Long,
    val name: String
)