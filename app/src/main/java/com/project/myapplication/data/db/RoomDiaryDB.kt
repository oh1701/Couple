package com.project.myapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.project.myapplication.data.dao.RoomCoupleSettingDao
import com.project.myapplication.data.dao.RoomDiaryDao
import com.project.myapplication.data.entity.RoomCoupleSettingEntity
import com.project.myapplication.data.entity.RoomDiaryEntity

@Database(entities = [RoomDiaryEntity::class, RoomCoupleSettingEntity::class], version = 2)
abstract class RoomDiaryDB:RoomDatabase() {
    abstract fun roomDaoImage(): RoomDiaryDao
    abstract fun roomDaoCoupleSetting(): RoomCoupleSettingDao

    companion object{
//        private val MIGRATION_1_2 = object : Migration(1,2){
//            override fun migrate(database: SupportSQLiteDatabase) {
//                val alter = "ALTER TABLE RoomDiaryEntity ADD COLUMN imageUriaa TEXT NOT NULL Default 0"
//                database.execSQL(alter)
//            }
//        }

        var INSTANCE : RoomDiaryDB? = null

        fun getInstance(context: Context): RoomDiaryDB? {
            if (INSTANCE == null) {
                synchronized(RoomDiaryDB::class) { //synchronized: 여러 스레드가 동시에 접근 불가. 동기적으로 접근
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        RoomDiaryDB::class.java, "RoomDiaryDB")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}