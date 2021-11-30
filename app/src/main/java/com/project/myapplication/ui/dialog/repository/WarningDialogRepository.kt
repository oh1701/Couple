package com.project.myapplication.ui.dialog.repository

import com.project.myapplication.base.BaseRepository
import com.project.myapplication.db.room.dao.RoomCoupleSettingDao
import com.project.myapplication.db.room.dao.RoomDiaryDao

class WarningDialogRepository:BaseRepository() {
    override val roomDiaryDao: RoomDiaryDao
        get() = super.roomDiaryDao
    override val roomDaoCoupleSetting: RoomCoupleSettingDao
        get() = super.roomDaoCoupleSetting
}