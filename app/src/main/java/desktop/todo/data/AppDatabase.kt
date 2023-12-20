package desktop.todo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import desktop.todo.data.dao.ContentDao
import desktop.todo.model.ContentEntity

@Database(entities = [ContentEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contentDao(): ContentDao
}