package github.android.skywell.com.git_sch.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "words")
class SearchWord(@PrimaryKey val id: Int, val name: String)
