package de.p72b.burstpooltracker.main.list

import androidx.fragment.app.Fragment
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.p72b.burstpooltracker.App
import de.p72b.burstpooltracker.R
import de.p72b.burstpooltracker.main.MinerAdapter
import de.p72b.burstpooltracker.main.BaseMinerViewModel
import de.p72b.burstpooltracker.settings.ADDRESS
import de.p72b.burstpooltracker.util.Utils
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.android.ext.android.inject


class ListFragment: Fragment() {

    private lateinit var adapter: MinerAdapter
    private val minerViewModel: BaseMinerViewModel by inject()

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

        minerViewModel.getMiners(true).observe(this, { miners ->
            adapter.setMiners(Utils.filter(miners))
        })
    }
}