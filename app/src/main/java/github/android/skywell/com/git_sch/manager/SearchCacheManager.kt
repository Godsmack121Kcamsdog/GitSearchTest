package github.android.skywell.com.git_sch.manager

import android.arch.persistence.room.Room
import android.content.Context

import github.android.skywell.com.git_sch.App
import github.android.skywell.com.git_sch.db.SearchDatabase
import github.android.skywell.com.git_sch.db.SearchItem
import github.android.skywell.com.git_sch.db.SearchWord
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchCacheManager private constructor(context: Context) {
    private val db: SearchDatabase


    val all: Single<List<SearchWord>>?
        get() = null

    init {
        db = Room.databaseBuilder(context, SearchDatabase::class.java, DB_NAME).build()
    }

    fun findByName(name: String): Single<SearchWord> {
        return db.searchDao().findByName(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun findById(id: Int): Single<List<SearchItem>> {
        return db.searchDao().findById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun insert(entity: SearchWord) {
        Completable.fromAction {
            db.searchDao().insert(entity)
            println(entity.name + " was saved to db")
        }.subscribeOn(Schedulers.io())
                .doOnError { e -> System.err.println(e.message + "\n" + e.cause.toString()) }
                .subscribe()
    }

    fun insert(item: SearchItem) {
        Completable.fromAction { db.searchDao().insert(item) }.subscribe()
    }

    fun insertAll(items: List<SearchItem>) {
        Completable.fromAction {
            for (i in items) {
                db.searchDao().insert(i)
            }
        }.subscribeOn(Schedulers.io())
                .doOnError { e -> System.err.println(e.message + "\n" + e.cause.toString()) }
                .subscribe()
    }

    fun insertAll(vararg entities: SearchWord) {

    }

    fun delete(entity: SearchWord) {

    }

    companion object {
        private val DB_NAME = "search.db"
        private var _instance: SearchCacheManager? = null

        val instance: SearchCacheManager
            get() {
                if (_instance == null) {
                    _instance = SearchCacheManager(App.instance!!.applicationContext)
                }
                return _instance as SearchCacheManager
            }
    }
}
