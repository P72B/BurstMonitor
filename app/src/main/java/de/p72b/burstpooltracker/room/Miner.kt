package de.p72b.burstpooltracker.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "miner")
data class Miner(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                 @ColumnInfo(name = "user") var user: String = "default",
                 @ColumnInfo(name = "burst_id") var burstId: String = "default",
                 @ColumnInfo(name = "credit") var credit: Double = 0.0,
                 @ColumnInfo(name = "historical_share") var historicalShare: Double = 0.0,
                 @ColumnInfo(name = "plot_size") var plotSize: Double = 0.0,
                 @ColumnInfo(name = "deadlines") var deadlines: Int = 0,
                 @ColumnInfo(name = "miner") var miner: String = "unknown",
                 @ColumnInfo(name = "time_milliseconds") var timeMilliseconds: Long = 0L,
                 @ColumnInfo(name = "delta_x") var delta_x: Double = 0.0,
                 @ColumnInfo(name = "delta_y") var delta_y: Long = 0L,
                 @ColumnInfo(name = "pitch") var pitch: Double = 0.0)