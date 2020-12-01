package de.p72b.burstpooltracker.http

import de.p72b.burstpooltracker.room.Miner
import pl.droidsonroids.jspoon.annotation.Selector
import java.util.Calendar

class MinerPage {
    @Selector("tbody")
    var table: Table? = null
}

class Table {
    @Selector("tr")
    var minerList: List<MinerItem>? = null
}

class MinerItem {
    @Selector("td")
    var minerItem: List<String>? = null
}

fun MinerPage.filter(address: String): Miner? {
    for (minerList in this.table?.minerList.orEmpty()) {
        minerList.minerItem.let {
            if (it?.get(1) == address) {
                return Miner(0, it[0], it[1], it[2].toDouble(), substringDouble(it[3], 2),
                    substringDouble(it[4], 3), it[5].toInt(), "unknown", Calendar.getInstance().timeInMillis)
            }
        }
    }
    return null
}

private fun substringDouble(item: String, end: Int): Double {
    return item.substring(0, item.length - end).toDouble()
}