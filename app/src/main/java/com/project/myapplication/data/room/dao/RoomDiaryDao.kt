package com.project.myapplication.data.room.dao

import androidx.room.*
import com.project.myapplication.data.room.entity.RoomDiaryEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RoomDiaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDao(vararg roomEntityImage: RoomDiaryEntity): Completable

    @Update
    fun updateDao(vararg roomEntityImage: RoomDiaryEntity): Completable

    @Query("SELECT * FROM RoomDiaryEntity")
    fun selectAllDao():Single<List<RoomDiaryEntity>>

    @Query("SELECT * FROM RoomDiaryEntity WHERE id = :id")
    fun selectIdDao(id:Int):Single<RoomDiaryEntity>

    @Query("DELETE FROM RoomDiaryEntity WHERE id = :id")
    fun deleteIdDao(id:Int):Completable
}