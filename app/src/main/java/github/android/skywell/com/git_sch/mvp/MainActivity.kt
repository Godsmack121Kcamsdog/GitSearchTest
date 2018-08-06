package github.android.skywell.com.git_sch.mvp

import android.arch.persistence.room.Room
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.widget.Button
import github.android.skywell.com.git_sch.adapters.ItemsAdapter
import github.android.skywell.com.git_sch.data.Item
import github.android.skywell.com.git_sch.db.SearchDatabase
import github.android.skywell.com.git_sch.db.SearchWord
import github.android.skywell.com.sky_git_searcher.R

/*
Создать клиент для поиска репозиториев на GitHub.
На экране есть поле для ввода текста и кнопка,
по клику на которую начинается поиск, который одновременно выполняется в двух потоках,
после чего результаты сохраняются в базу данных.
БД всегда хранить результаты только последнего поиска,
соответственно, если запрос будет такой же, то выводятся кэшированные данные.
Пользователь также имеет возможность отменить поиск нажав на ту же кнопку.
При повороте экрана, View не пересоздается.
* */

/*
*
• Android 5.0+
• RxJava, RxAndroid
• Покрытие тестами проекта не менее 50%
• Желательно Kotlin
• Желательно Room (DB)
*/
class MainActivity : AppCompatActivity(), MainController.View {

    private lateinit var adapter:ItemsAdapter

    private var presenter: MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.button)
        val search: SearchView = findViewById(R.id.search)
        val recycler: RecyclerView = findViewById(R.id.recycler)

        presenter = MainPresenter(this)
        button.setOnClickListener { presenter!!.search(search.query.toString()) }
        adapter = ItemsAdapter(this)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
    }

    override fun setRepositories(list: List<Item>) {
        adapter.updateContent(list)
    }

    override fun getContext(): Context {
        return this
    }
}
