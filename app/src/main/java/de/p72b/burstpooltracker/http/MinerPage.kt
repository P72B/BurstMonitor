package de.p72b.burstpooltracker.http

import pl.droidsonroids.jspoon.annotation.Selector

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