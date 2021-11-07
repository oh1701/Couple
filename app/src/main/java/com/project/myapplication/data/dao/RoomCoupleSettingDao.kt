package com.project.myapplication.data.dao

import androidx.room.*
import com.project.myapplication.data.entity.RoomCoupleSettingEntity
import com.project.myapplication.data.entity.RoomDiaryEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RoomCoupleSettingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDao(vararg roomCoupleSettingEntity: RoomCoupleSettingEntity): Completable

    @Update
    fun updateDao(vararg roomCoupleSettingEntity: RoomCoupleSettingEntity): Completable

    @Query("SELECT * FROM RoomCoupleSettingEntity")
    fun selectAllDao(): Single<List<RoomCoupleSettingEntity>>

    @Query("SELECT * FROM RoomCoupleSettingEntity WHERE id = :id")
    fun selectIdDao(id:Int): Single<RoomCoupleSettingEntity>

    @Query("DELETE FROM RoomCoupleSettingEntity WHERE imageUri = :id")
    fun deleteIdDao(id:Int)
}