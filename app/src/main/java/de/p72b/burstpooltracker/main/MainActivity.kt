package de.p72b.burstpooltracker.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.p72b.burstpooltracker.settings.SettingsActivity
import de.p72b.burstpooltracker.worker.StatusFetcherWorker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import androidx.fragment.app.Fragment
import androidx.work.*
import de.p72b.burstpooltracker.R
import de.p72b.burstpooltracker.main.dashboard.DashboardFragment
import de.p72b.burstpooltracker.main.list.ListFragment
import de.p72b.burstpooltracker.main.statistic.StatisticFragment


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    companion object {
        private val TAG = "p72b"
        const val FETCHER_WORKER = "FETCHER_WORKER"
    }

    lateinit var minerViewModel: MinerViewModel
    var listFragment = ListFragment()
    var dashboardFragment = DashboardFragment()
    var statisticFragment = StatisticFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.vFab)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.vNavigationView)

        minerViewModel = ViewModelProviders.of(this).get(MinerViewModel::class.java)

        loadFragment(dashboardFragment)

        fab.setOnClickListener {
            createPeriodicWorker()
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.navigation_dashboard -> fragment = dashboardFragment
            R.id.navigation_statistics -> fragment = statisticFragment
            R.id.navigation_list -> fragment = listFragment
        }
        return loadFragment(fragment)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun createPeriodicWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest =
            PeriodicWorkRequestBuilder<StatusFetcherWorker>(1, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance()
            .enqueueUniquePeriodicWork(FETCHER_WORKER, ExistingPeriodicWorkPolicy.REPLACE, workRequest)
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        //switching fragment
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.vFragmentContainer, fragment)
                .commit()
            return true
        }
        return false
    }
}
