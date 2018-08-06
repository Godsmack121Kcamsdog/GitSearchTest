package github.android.skywell.com.git_sch.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [SearchWord::class, SearchItem::class], version = 1, exportSchema = false)
abstract class SearchDatabase : RoomDatabase() {
    abstract fun searchDao(): DaoAccess
}