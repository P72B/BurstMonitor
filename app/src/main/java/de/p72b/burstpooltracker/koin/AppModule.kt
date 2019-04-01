package de.p72b.burstpooltracker.koin

import de.p72b.burstpooltracker.MinerRepository
import de.p72b.burstpooltracker.http.WebService
import org.koin.dsl.module

val appModule = module {
    single { WebService() }
    single { MinerRepository() }
}