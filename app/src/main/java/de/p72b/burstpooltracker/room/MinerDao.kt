package de.p72b.burstpooltracker.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MinerDao {

    @Query("SELECT * from miner ORDER BY time_milliseconds ASC")
    fun getAll(): LiveData<List<Miner>>

    @Query("SELECT * from miner ORDER BY time_milliseconds DESC")
    fun getAllDesc(): LiveData<List<Miner>>

    @Query("SELECT * from miner WHERE time_milliseconds > :age ORDER BY time_milliseconds ASC")
    fun filter(age: Long): LiveData<List<Miner>>

    @Query("SELECT * from miner WHERE time_milliseconds > :age ORDER BY time_milliseconds DESC")
    fun filterDesc(age: Long): LiveData<List<Miner>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(miner: Miner)

    @Query("DELETE from miner")
    fun deleteAll()

    @Query("DELETE from miner WHERE delta_y < :value")
    fun deleteWhereDeltaYSmallerAs(value: Long)

    @Query("SELECT * from miner WHERE burst_id = :address ORDER BY time_milliseconds ASC LIMIT 1")
    fun getLatestEntryFor(address: String): Miner?
}