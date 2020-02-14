package de.p72b.burstpooltracker.main.statistic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.db.chart.model.ChartSet
import com.db.chart.model.LineSet
import com.db.chart.view.LineChartView
import de.p72b.burstpooltracker.main.MainActivity
import de.p72b.burstpooltracker.room.Miner
import android.graphics.Color
import de.p72b.burstpooltracker.R
import de.p72b.burstpooltracker.Utils


class StatisticFragment : Fragment() {

    private lateinit var lineChart: LineChartView
    private lateinit var lineChartPlotSize: LineChartView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_statistics, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lineChart = view.findViewById(R.id.vLineChartBurst)
        lineChartPlotSize = view.findViewById(R.id.vLineChartBurstPlotSize)

        (activity as MainActivity).minerViewModel.allMiners.observe(this,
            Observer<List<Miner>> { miners ->
                if (miners != null) {
                    setMiners(Utils.filter(miners))
                }
            })
    }

    private fun setMiners(miners: List<Miner>) {
        fillBurst(miners)
        fillPlotSize(miners)
    }

    private fun fillBurst(miners: List<Miner>) {
        val size = miners.size
        val lables = arrayOfNulls<String>(size)
        val values = FloatArray(size)

        for ((index, value) in miners.withIndex()) {
            lables[index] = " " //Utils.timeStampToIsoDate(value.timeMilliseconds)
            values[index] = value.credit.toFloat()
        }

        val dataSet = LineSet(lables, values)
        dataSet.setColor(Color.parseColor("#53c1bd")).setFill(Color.parseColor("#3d6c73"))
            .setGradientFill(intArrayOf(Color.parseColor("#364d5a"), Color.parseColor("#3f7178")), null)

        //dataSet.setColor(Color.parseColor("#758cbb"))
        //    .setFill(Color.parseColor("#2d374c"))
        //    .setDotsColor(Color.parseColor("#758cbb")).thickness = 1.0f
        lineChart.addData(dataSet as ChartSet)
        lineChart.dismissAllTooltips()
        lineChart.show()
    }

    private fun fillPlotSize(miners: List<Miner>) {
        val size = miners.size
        val lables = arrayOfNulls<String>(size)
        val values = FloatArray(size)

        for ((index, value) in miners.withIndex()) {
            lables[index] = " " //Utils.timeStampToIsoDate(value.timeMilliseconds)
            values[index] = value.plotSize.toFloat()
        }

        val dataSet = LineSet(lables, values)
        dataSet.setColor(Color.parseColor("#53c1bd")).setFill(Color.parseColor("#3d6c73"))
            .setGradientFill(intArrayOf(Color.parseColor("#364d5a"), Color.parseColor("#3f7178")), null)

        //dataSet.setColor(Color.parseColor("#758cbb"))
        //    .setFill(Color.parseColor("#2d374c"))
        //    .setDotsColor(Color.parseColor("#758cbb")).thickness = 1.0f
        lineChartPlotSize.addData(dataSet as ChartSet)
        lineChartPlotSize.dismissAllTooltips()
        lineChartPlotSize.show()
    }
}