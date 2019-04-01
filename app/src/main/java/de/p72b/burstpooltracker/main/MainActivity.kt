package de.p72b.burstpooltracker.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import de.p72b.burstpooltracker.R
import de.p72b.burstpooltracker.worker.StatusFetcherWorker

import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import androidx.lifecycle.Observer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.p72b.burstpooltracker.room.Miner


class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = "p72b"
        const val FETCHER_WORKER = "FETCHER_WORKER"
        const val MY_ADDRESS = "2NJ6-DD4F-Z8PL-9725G"
    }

    private lateinit var minerViewModel: MinerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val recyclerView: RecyclerView = findViewById(R.id.vRecyclerView)
        val fab: FloatingActionButton = findViewById(R.id.vFab)
        val adapter = MinerAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        minerViewModel = ViewModelProviders.of(this).get(MinerViewModel::class.java)
        minerViewModel.allMiners.observe(this,
            Observer<List<Miner>> { miners ->
                adapter.setMiners(miners)
            })

        fab.setOnClickListener {
            createPeriodicWorker()
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
