package com.project.myapplication.data.dao

import androidx.room.*
import com.project.myapplication.data.entity.RoomEntityImage
import io.reactivex.Single

@Dao
interface RoomDaoImage {
    @Insert
    fun insertDao(vararg roomEntityImage: RoomEntityImage)

    @Update
    fun updateDao(vararg roomEntityImage: RoomEntityImage)

    @Query("SELECT * FROM RoomEntityImage WHERE date = :date AND title = :title")
    fun selectIdDao(date:Long, title:String): Single<RoomEntityImage>

    @Query("DELETE FROM RoomEntityImage WHERE imageUri = :id")
    fun deleteIdDao(id:Int)
}