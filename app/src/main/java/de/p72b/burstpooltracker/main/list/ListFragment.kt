package de.p72b.burstpooltracker.main.list

import androidx.fragment.app.Fragment
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.p72b.burstpooltracker.App
import de.p72b.burstpooltracker.R
import de.p72b.burstpooltracker.Utils
import de.p72b.burstpooltracker.main.MainActivity
import de.p72b.burstpooltracker.main.MinerAdapter
import de.p72b.burstpooltracker.room.Miner
import de.p72b.burstpooltracker.settings.ADDRESS
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment: Fragment() {

    private lateinit var adapter: MinerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.vRecyclerView)
        adapter = MinerAdapter(App.sInstance.applicationContext)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onResume() {
        super.onResume()
        setupAddress()

        (activity as MainActivity).minerViewModel.getMiners(true).observe(this,
            Observer<List<Miner>> { miners ->
                adapter.setMiners(Utils.filter(miners))
            })
    }

    private fun setupAddress() {
        vTitle.text = PreferenceManager.getDefaultSharedPreferences(App.sInstance.applicationContext)
            .getString(getString(ADDRESS), getString(R.string.not_set))
    }
}