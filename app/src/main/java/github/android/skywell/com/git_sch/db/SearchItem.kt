package github.android.skywell.com.git_sch.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "items")
class SearchItem(val itemName: String, val url: String,
                 @ForeignKey(entity = SearchWord::class, parentColumns = ["id"], childColumns = ["itemId"]) val id: Int) {

    @PrimaryKey(autoGenerate = true)
    var itemId: Int? = null

    override fun toString(): String {
        return "Item $itemId; $itemName; $url"
    }
}
