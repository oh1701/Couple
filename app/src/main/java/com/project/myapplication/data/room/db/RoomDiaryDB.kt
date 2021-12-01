package com.project.myapplication.data.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project.myapplication.data.room.dao.RoomCoupleSettingDao
import com.project.myapplication.data.room.dao.RoomDiaryDao
import com.project.myapplication.data.room.dao.RoomFontDao
import com.project.myapplication.data.room.entity.RoomCoupleSettingEntity
import com.project.myapplication.data.room.entity.RoomDiaryEntity
import com.project.myapplication.data.room.entity.RoomFontEntity

@Database(entities = [RoomDiaryEntity::class, RoomCoupleSettingEntity::class, RoomFontEntity::class], version = 1)
abstract class RoomDiaryDB:RoomDatabase() {
    abstract fun roomDaoImage(): RoomDiaryDao
    abstract fun roomDaoCoupleSetting(): RoomCoupleSettingDao
    abstract fun roomDaoFont(): RoomFontDao

    companion object{
//        private val MIGRATION_1_2 = object : Migration(1,2){
//            override fun migrate(database: SupportSQLiteDatabase) {
//                val alter = "ALTER TABLE RoomDiaryEntity ADD COLUMN content TEXT NOT NULL Default 0"
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
//                        .addMigrations(MIGRATION_1_2)
                        .build()
                }
            }
            return INSTANCE
        }
    }
}