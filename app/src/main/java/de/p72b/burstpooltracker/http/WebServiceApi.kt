package de.p72b.burstpooltracker.http

import retrofit2.http.GET


interface WebServiceApi {

    @GET("miners")
    suspend fun getMiners(): MinerPage

}