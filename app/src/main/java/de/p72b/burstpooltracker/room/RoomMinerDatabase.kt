package de.p72b.burstpooltracker.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(entities = [Miner::class], version = 1)
abstract class RoomMinerDatabase: RoomDatabase() {

    abstract fun minerDao(): MinerDao

    companion object {
        private var INSTANCE: RoomMinerDatabase? = null

        fun getInstance(context: Context): RoomMinerDatabase? {
            if (INSTANCE == null) {
                synchronized(RoomMinerDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        RoomMinerDatabase::class.java, "miner.db")
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}