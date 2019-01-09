package starlet.tech.androidcodesample.api

import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import starlet.tech.androidcodesample.BuildConfig
import java.net.ProtocolException
import java.util.concurrent.TimeUnit

class MainClient private constructor() {

    private val service: MainService

    companion object {

        const val BASE_API = BuildConfig.API_URL

        private var instance: MainClient? = null

        fun getClient(): MainClient {
            if (instance == null) {
                instance = MainClient()
            }
            return instance!!
        }
    }

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addNetworkInterceptor { chain ->

                val response: Response = try {
                    chain.proceed(chain.request())
                } catch (e: ProtocolException) {
                    Response.Builder()
                        .request(chain.request())
                        .code(204)
                        .protocol(Protocol.HTTP_1_1)
                        .build()
                }

                response
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_API)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        service = retrofit.create(MainService::class.java)
    }

    fun getWeatherByName(name: String) = service.getWeatherByCityName(name)
}