package de.p72b.burstpooltracker.util

import androidx.preference.PreferenceManager
import de.p72b.burstpooltracker.App
import de.p72b.burstpooltracker.http.MinerPage
import de.p72b.burstpooltracker.room.Miner
import de.p72b.burstpooltracker.settings.FILTER
import de.p72b.burstpooltracker.settings.FILTER_TIME
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.GERMAN)

    fun timeStampToIsoDate(timeMilliseconds: Long): String {
        val calendar = Date(timeMilliseconds)
        return formatter.format(calendar)
    }

    fun filter(minerList: List<Miner>): List<Miner> {
        val key = App.sInstance.getString(FILTER)
        val prefValue: String = PreferenceManager.getDefaultSharedPreferences(App.sInstance).getString(key, "A")?: "A"
        return filterBy(minerList, getMapped(prefValue))
    }

    private fun getMapped(value: String): Long {
        return when(value) {
            "0" -> 0L
            "3600000" -> 3600000L
            "43200000" -> 43200000L
            "86400000" -> 86400000L
            else -> 0L
        }
    }

    fun filterTime(): Long {
        val key = App.sInstance.getString(FILTER_TIME)
        val prefValue: String = PreferenceManager.getDefaultSharedPreferences(App.sInstance).getString(key, "A")?: "A"
        return getMappedFilterTime(prefValue)
    }

    private fun getMappedFilterTime(value: String): Long {
        return when(value) {
            "0" -> 0L
            "7200000" -> 7200000
            "86400000" -> 86400000
            "604800000" -> 604800000
            "2592000000" -> 2592000000
            "7776000000" -> 7776000000
            else -> 0L
        }
    }

    private fun filterBy(minerList: List<Miner>, interval: Long): List<Miner> {
        if (interval == 0L || minerList.isEmpty() || minerList.size < 2) {
            return minerList
        }
        val isAsc = (minerList[0].timeMilliseconds - minerList[1].timeMilliseconds) >= 0
        val filtered = mutableListOf<Miner>()
        var previous = 0L
        for (miner in minerList) {
            val delta= if (isAsc) {
                previous - miner.timeMilliseconds
            } else {
                miner.timeMilliseconds - previous
            }
            if (delta >= interval || previous == 0L) {
                filtered.add(miner)
                previous = miner.timeMilliseconds
            }
        }
        return filtered
    }
}