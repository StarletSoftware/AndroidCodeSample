package starlet.tech.androidcodesample.api

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import starlet.tech.androidcodesample.BuildConfig
import starlet.tech.androidcodesample.model.CityWeatherModel

interface MainService {

    @Headers("Content-Type: application/json")
    @GET("weather")
    fun getWeatherByCityName(
        @Query("q") city: String,
        @Query("appId") id: String = BuildConfig.APP_ID) : Observable<Response<CityWeatherModel>>
}