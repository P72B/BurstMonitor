package de.p72b.burstpooltracker.main.list

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.p72b.burstpooltracker.App
import de.p72b.burstpooltracker.R
import de.p72b.burstpooltracker.main.MainActivity
import de.p72b.burstpooltracker.main.MinerAdapter
import de.p72b.burstpooltracker.room.Miner
import de.p72b.burstpooltracker.settings.ADDRESS
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.vRecyclerView)
        val adapter = MinerAdapter(App.sInstance.applicationContext)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        (activity as MainActivity).minerViewModel.allMiners.observe(this,
            Observer<List<Miner>> { miners ->
                adapter.setMiners(miners)
            })
    }

    override fun onResume() {
        super.onResume()
        setupAddress()
    }

    private fun setupAddress() {
        vTitle.text = PreferenceManager.getDefaultSharedPreferences(App.sInstance.applicationContext)
            .getString(getString(ADDRESS), getString(de.p72b.burstpooltracker.R.string.not_set))
    }
}