package starlet.tech.androidcodesample.viewmodel

import starlet.tech.androidcodesample.api.MainClient
import starlet.tech.androidcodesample.api.RequestType
import starlet.tech.androidcodesample.model.CityWeatherModel

class MainViewModel(private val listener: Listener) : BaseViewModel() {

    interface Listener {
        fun showData(res: CityWeatherModel)
        fun showError(message: String?)
    }

    fun getWeatherByCityName(name: String) {
        makeSilentRequest(MainClient.getClient().getWeatherByName(name), RequestType.CITY_WEATHER)
    }

    override fun onRequestSuccess(response: Any, requestType: RequestType) {
        super.onRequestSuccess(response, requestType)

        when(requestType) {
            RequestType.CITY_WEATHER -> {
                val res = response as CityWeatherModel
                listener.showData(res)
            }
        }
    }

    override fun onRequestFailed(e: Throwable, requestType: RequestType) {
        super.onRequestFailed(e, requestType)
        listener.showError(e.message)
    }
}