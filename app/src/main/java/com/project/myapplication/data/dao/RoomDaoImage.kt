package com.project.myapplication.data.dao

import androidx.room.*
import com.project.myapplication.data.entity.RoomEntityImage
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RoomDaoImage {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDao(vararg roomEntityImage: RoomEntityImage): Completable

    @Update
    fun updateDao(vararg roomEntityImage: RoomEntityImage): Completable

    @Query("SELECT * FROM RoomEntityImage WHERE title = :title")
    fun selectIdDao(title:String):Single<RoomEntityImage>

    @Query("DELETE FROM RoomEntityImage WHERE imageUri = :id")
    fun deleteIdDao(id:Int)
}