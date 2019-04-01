package de.p72b.burstpooltracker.http

import io.reactivex.Flowable
import retrofit2.http.*

interface WebServiceApi {

    @GET("miners")
    fun getMiners(): Flowable<MinerPage>

}