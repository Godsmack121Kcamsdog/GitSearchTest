package github.android.skywell.com.git_sch.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface DaoAccess {

    @Query("SELECT * FROM words")
    fun getAll(): Single<List<SearchWord>>

    @Query("SELECT * FROM items")
    fun getItems(): Single<List<SearchItem>>

    @Query("SELECT * FROM words WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Single<SearchWord>

    @Query("SELECT * FROM items WHERE id LIKE :id")
    fun findById(id: Int): Single<List<SearchItem>>

    @Insert
    fun insertAll(vararg entities: SearchWord)

    @Insert(onConflict = REPLACE)
    fun insert(entity: SearchWord)

    @Insert(onConflict = REPLACE)
    fun insert(item: SearchItem)

    @Delete
    fun delete(entity: SearchWord)
}
