package com.project.myapplication.base

import com.project.myapplication.data.db.RoomDiaryDB

open class BaseRepository{
    protected open val roomDao = RoomDiaryDB.INSTANCE!!.roomDaoImage()


}