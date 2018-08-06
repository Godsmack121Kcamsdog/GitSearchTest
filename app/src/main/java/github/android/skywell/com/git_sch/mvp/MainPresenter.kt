package github.android.skywell.com.git_sch.mvp

import android.widget.Toast
import github.android.skywell.com.git_sch.api.search.SearchAPI
import github.android.skywell.com.git_sch.data.Item
import github.android.skywell.com.git_sch.data.Response
import github.android.skywell.com.git_sch.db.SearchItem
import github.android.skywell.com.git_sch.db.SearchWord
import github.android.skywell.com.git_sch.manager.SearchCacheManager
import io.reactivex.observers.DisposableSingleObserver

class MainPresenter(val view: MainController.View) : MainController.EventListener {

    override fun search(name: String) {
        SearchCacheManager.instance
                .findByName(name)
                .subscribe(object : DisposableSingleObserver<SearchWord>() {
                    override fun onSuccess(t: SearchWord) {
                        SearchCacheManager.instance.findById(t.id)
                                .doOnError(::onFailure)
                                .doOnSuccess { it ->
                                    val res = Response()
                                    val list = ArrayList<Item>()
                                    it.forEach {
                                        val item = Item()
                                        item.id = it.itemId!!.toLong()
                                        item.name = it.itemName
                                        item.url = it.url
                                        list.add(item)
                                    }
                                    res.items = list
                                    res.totalCount = list.size
                                    onSuccess(res)
                                }.subscribe()
                    }

                    override fun onError(e: Throwable) {
                        SearchAPI.getInstance().searchRepository(name)
                                .doOnError(::onFailure)
                                .doOnNext {
                                    save(it.items!!, name)
                                    onSuccess(it)
                                }
                                .subscribe()
                    }
                })
    }

    override fun save(items: List<Item>, name: String) {
        val word = SearchWord(items[0].id.toInt(), name)
        SearchCacheManager.instance.insert(word)
        val list = ArrayList<SearchItem>()
        items.forEach {
            val item = SearchItem(it.name!!, it.url!!, word.id)
            list.add(item)
        }
        SearchCacheManager.instance.insertAll(list)
    }

    override fun onSuccess(o: Response) {
        Toast.makeText(view.getContext(), "success", Toast.LENGTH_SHORT).show()
        view.setRepositories(o.items!!)
    }

    override fun onFailure(err: Throwable) {
        Toast.makeText(view.getContext(), "error", Toast.LENGTH_SHORT).show()
    }
}