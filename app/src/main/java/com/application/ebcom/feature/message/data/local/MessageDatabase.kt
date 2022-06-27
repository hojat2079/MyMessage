package com.application.ebcom.feature.message.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.application.ebcom.util.DATABASE_VERSION
import com.application.ebcom.feature.message.data.local.entity.MessageEntity

@Database(
    version = DATABASE_VERSION,
    entities = [MessageEntity::class],
    exportSchema = false
)
abstract class MessageDatabase : RoomDatabase() {
    abstract val dao: MessageDao
}