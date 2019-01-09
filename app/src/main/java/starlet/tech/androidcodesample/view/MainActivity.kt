package starlet.tech.androidcodesample.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import starlet.tech.androidcodesample.R
import starlet.tech.androidcodesample.model.CityWeatherModel
import starlet.tech.androidcodesample.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), MainViewModel.Listener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = MainViewModel(this)

        mainButton.setOnClickListener {
            val text = mainCityEditText.text.toString()
            if (!TextUtils.isEmpty(text)) {
                viewModel.getWeatherByCityName(text)
            }
        }
    }

    override fun showData(res: CityWeatherModel) {
        mainCityNameTextView.text = res.name
        mainCityDescTextView.text = res.weather[0].description
        mainCityCurrentTempTextView.text = res.main.getCurrentTempInCelcius().toString()
        mainCityMaxTempTextView.text = res.main.getMaxTempInCelcius().toString()
        mainCityMinTempTextView.text = res.main.getMinTempInCelcius().toString()
    }

    override fun showError(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
