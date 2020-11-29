package de.p72b.burstpooltracker.http

import io.reactivex.Flowable
import okhttp3.OkHttpClient
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

class WebService {

    companion object {
        private val TAG = WebService::class.java.simpleName
        private const val TIMEOUT_DEFAULT = 20L
    }

    private val api: WebServiceApi by lazy {
        val client = OkHttpClient.Builder().apply {
            readTimeout(TIMEOUT_DEFAULT, TimeUnit.SECONDS)
            writeTimeout(TIMEOUT_DEFAULT, TimeUnit.SECONDS)
            connectTimeout(TIMEOUT_DEFAULT, TimeUnit.SECONDS)
        }.build()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://0-100pool.burstcoin.ro")
                .addConverterFactory(JspoonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()

        retrofit.create(WebServiceApi::class.java)
    }

    fun getMiners(): Flowable<MinerPage> = api.getMiners()
}
