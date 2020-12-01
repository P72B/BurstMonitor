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
                 @ColumnInfo(name = "delta_credit") var delta_credit: Double = 0.0,
                 @ColumnInfo(name = "delta_time_milliseconds") var delta_time_milliseconds: Long = 0L,
                 @ColumnInfo(name = "delta_historical_share") var delta_historical_share: Double = 0.0,
                 @ColumnInfo(name = "delta_plot_size") var delta_plot_size: Double = 0.0,
                 @ColumnInfo(name = "pitch") var pitch: Double = 0.0)