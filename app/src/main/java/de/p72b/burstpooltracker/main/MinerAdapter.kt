package de.p72b.burstpooltracker.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.p72b.burstpooltracker.R
import de.p72b.burstpooltracker.util.Utils
import de.p72b.burstpooltracker.room.Miner

class MinerAdapter(context: Context): RecyclerView.Adapter<MinerAdapter.MinerViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private var minerList : List<Miner>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MinerViewHolder {
        val viewItem = inflater.inflate(R.layout.recyclerview_miner_item, parent, false)
        return MinerViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return when(minerList) {
            null -> 0
            else -> minerList!!.size
        }
    }

    override fun onBindViewHolder(holder: MinerViewHolder, position: Int) {
        val miner = minerList!![position]
        holder.creditItemView.text = miner.credit.toString()
        holder.historicalShareItemView.text = miner.historicalShare.toString() + " %"
        holder.plotSizeItemView.text = miner.plotSize.toString() + " TB"

        holder.timestampItemView.text = Utils.timeStampToIsoDate(miner.timeMilliseconds)
    }

    fun setMiners(miners: List<Miner>) {
        minerList = miners
        notifyDataSetChanged()
    }

    class MinerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val creditItemView: TextView = itemView.findViewById(R.id.vCredit)
        val historicalShareItemView: TextView = itemView.findViewById(R.id.vHistoricalShare)
        val plotSizeItemView: TextView = itemView.findViewById(R.id.vPlotSize)
        val timestampItemView: TextView = itemView.findViewById(R.id.vTimestamp)
    }
}