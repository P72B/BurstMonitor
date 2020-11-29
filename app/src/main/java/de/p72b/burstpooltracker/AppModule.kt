package de.p72b.burstpooltracker

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import de.p72b.burstpooltracker.main.MinerRepository
import de.p72b.burstpooltracker.http.WebService
import de.p72b.burstpooltracker.main.BaseMinerViewModel
import de.p72b.burstpooltracker.main.ObserveLatestMinerUserCase
import de.p72b.burstpooltracker.main.GetLatestMinerStatusUserCase
import de.p72b.burstpooltracker.main.dashboard.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        get() as App
    }

    single<SharedPreferences> {
        PreferenceManager.getDefaultSharedPreferences(get())
    }

    single {
        MinerRepository()
    }

    single {
        WebService()
    }

    factory {
        GetLatestMinerStatusUserCase(
            repository = get(),
            sharedPreferences = get(),
            context = get()
        )
    }

    factory {
        ObserveLatestMinerUserCase(
            repository = get(),
            sharedPreferences = get(),
            context = get()
        )
    }

    viewModel {
        DashboardViewModel(
            repository = get(),
            observeLatestMinerUserCase = get()
        )
    }

    viewModel {
        BaseMinerViewModel (
            repository = get()
        )
    }
}