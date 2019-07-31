package luclx.com.beapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import luclx.com.beapp.data.local.dao.CardDao
import luclx.com.beapp.data.local.entity.CardEntity

/**
 * https://medium.com/androiddevelopers/7-pro-tips-for-room-fbadea4bfbd1#4785
 */
@Database(entities = [CardEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao
}