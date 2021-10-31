package com.project.myapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project.myapplication.data.dao.RoomDaoImage
import com.project.myapplication.data.entity.RoomEntityImage

@Database(entities = [RoomEntityImage::class], version = 1)
abstract class RoomImageDB:RoomDatabase() {
    abstract fun roomDaoImage(): RoomDaoImage

    companion object{
        var INSTANCE : RoomImageDB? = null

        fun getInstance(context: Context): RoomImageDB? {
            if (INSTANCE == null) {
                synchronized(RoomImageDB::class) { //synchronized: 여러 스레드가 동시에 접근 불가. 동기적으로 접근
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        RoomImageDB::class.java, "todo")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}