package de.p72b.burstpooltracker.koin

import de.p72b.burstpooltracker.MinerRepository
import de.p72b.burstpooltracker.http.WebService
import de.p72b.burstpooltracker.main.MinerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        WebService()
    }

    single {
        MinerRepository()
    }

    viewModel {
        MinerViewModel (
            repository = get()
        )
    }
}