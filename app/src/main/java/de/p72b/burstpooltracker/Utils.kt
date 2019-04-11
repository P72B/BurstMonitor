package de.p72b.burstpooltracker

import de.p72b.burstpooltracker.http.MinerPage
import de.p72b.burstpooltracker.room.Miner
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.GERMAN)

    fun minerFromPage(minerPage: MinerPage, poiAddress: String): Miner? {
        for (minerList in minerPage.table?.minerList.orEmpty()) {
            minerList.minerItem.let {
                if (it?.get(1) == poiAddress) {
                    return Miner(0, it[0], it[1], it[2].toDouble(), substringDouble(it[3], 2),
                        substringDouble(it[4], 3), it[5].toInt(), it[6], Calendar.getInstance().timeInMillis)
                }
            }
        }
        return null
    }

    fun timeStampToIsoDate(timeMilliseconds: Long): String {
        val calendar = Date(timeMilliseconds)
        return formatter.format(calendar)
    }

    private fun substringDouble(item: String, end: Int): Double {
        return item.substring(0, item.length - end).toDouble()
    }
}