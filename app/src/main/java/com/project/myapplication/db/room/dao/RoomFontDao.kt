package com.project.myapplication.db.room.dao

import androidx.room.*
import com.project.myapplication.db.room.entity.RoomFontEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RoomFontDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDao(vararg font: RoomFontEntity): Completable

    @Update
    fun updateDao(vararg font: RoomFontEntity): Completable

    @Query("SELECT * FROM RoomFontEntity")
    fun selectAllDao(): Single<List<RoomFontEntity>>

    @Query("SELECT * FROM RoomFontEntity WHERE id = :id")
    fun selectIdDao(id:Int): Single<RoomFontEntity>
}