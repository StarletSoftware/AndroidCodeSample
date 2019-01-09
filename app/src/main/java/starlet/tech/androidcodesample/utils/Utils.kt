package starlet.tech.androidcodesample.utils

import android.util.Log

object Utils {

    private const val TAG = "CODE_SAMPLE_APP"

    fun log(text: String) {
        Log.d(TAG, text)
    }

    fun kelvinToCelsius(kelvin: Double): Int = (kelvin - 273.15).toInt()

}