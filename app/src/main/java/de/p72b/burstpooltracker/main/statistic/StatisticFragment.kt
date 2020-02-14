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

    private var gradient: IntArray =
        intArrayOf(Color.parseColor("#364d5a"), Color.parseColor("#3f7178"))
    private var color: Int = Color.parseColor("#53c1bd")
    private var dotsColor: Int = Color.parseColor("#758cbb")
    private var lineThickness = 0.8f
    private var fillColor: Int = Color.parseColor("#3d6c73")
    private lateinit var lineChart: LineChartView
    private lateinit var lineChartPlotSize: LineChartView
    private lateinit var lineChartHistoricalShare: LineChartView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_statistics, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lineChart = view.findViewById(R.id.vLineChartBurst)
        lineChartPlotSize = view.findViewById(R.id.vLineChartBurstPlotSize)
        lineChartHistoricalShare = view.findViewById(R.id.vLineChartHistoricalShare)

        (activity as MainActivity).minerViewModel.allMiners.observe(this,
            Observer<List<Miner>> { miners ->
                if (miners != null) {
                    setMiners(Utils.filter(miners))
                }
            })
    }

    private fun setMiners(miners: List<Miner>) {
        val size = miners.size
        val lables = arrayOfNulls<String>(size)
        val values = FloatArray(size)

        fillBurst(miners, lables, values)
        fillPlotSize(miners, lables, values)
        fillHistoricalShare(miners, lables, values)
    }

    private fun fillBurst(miners: List<Miner>, lables: Array<String?>, values: FloatArray) {

        for ((index, value) in miners.withIndex()) {
            lables[index] = " " //Utils.timeStampToIsoDate(value.timeMilliseconds)
            values[index] = value.credit.toFloat()
        }

        val dataSet = LineSet(lables, values)
        dataSet.setColor(color)
            .setFill(fillColor)
            .setGradientFill(gradient, null)
            //.setDotsColor(dotsColor)
            .thickness = lineThickness
        lineChart.addData(dataSet as ChartSet)
        lineChart.dismissAllTooltips()
        lineChart.show()
    }

    private fun fillPlotSize(miners: List<Miner>, lables: Array<String?>, values: FloatArray) {
        for ((index, value) in miners.withIndex()) {
            lables[index] = " " //Utils.timeStampToIsoDate(value.timeMilliseconds)
            values[index] = value.plotSize.toFloat()
        }

        val dataSet = LineSet(lables, values)
        dataSet.setColor(color)
            .setFill(fillColor)
            .setGradientFill(gradient, null)
            //.setDotsColor(dotsColor)
            .thickness = lineThickness
        lineChartPlotSize.addData(dataSet as ChartSet)
        lineChartPlotSize.dismissAllTooltips()
        lineChartPlotSize.show()
    }

    private fun fillHistoricalShare(
        miners: List<Miner>,
        lables: Array<String?>,
        values: FloatArray
    ) {
        for ((index, value) in miners.withIndex()) {
            lables[index] = " "
            values[index] = value.historicalShare.toFloat()
        }

        val dataSet = LineSet(lables, values)
        dataSet.setColor(color)
            .setFill(fillColor)
            .setGradientFill(gradient, null)
            //.setDotsColor(dotsColor)
            .thickness = lineThickness
        lineChartHistoricalShare.addData(dataSet as ChartSet)
        lineChartHistoricalShare.dismissAllTooltips()
        lineChartHistoricalShare.show()
    }
}