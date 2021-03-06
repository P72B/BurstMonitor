package de.p72b.burstpooltracker

import de.p72b.burstpooltracker.main.MinerRepository
import de.p72b.burstpooltracker.http.WebService
import de.p72b.burstpooltracker.main.MinerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        MinerRepository()
    }

    single {
        WebService()
    }

    viewModel {
        MinerViewModel (
            repository = get()
        )
    }
}