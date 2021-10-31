package com.project.myapplication.base

import com.project.myapplication.data.db.RoomImageDB

open class BaseRepository{
    protected open val roomDao = RoomImageDB.INSTANCE!!.roomDaoImage()


}