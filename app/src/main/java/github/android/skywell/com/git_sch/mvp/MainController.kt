package github.android.skywell.com.git_sch.mvp

import android.content.Context
import github.android.skywell.com.git_sch.data.Item
import github.android.skywell.com.git_sch.data.Response

interface MainController {
    interface View {

        fun setRepositories(list: List<Item>)
        fun getContext(): Context
    }

    interface EventListener {
        fun search(name: String)
        fun save(items: List<Item>, name: String)
        fun onSuccess(o: Response)
        fun onFailure(err: Throwable)
    }
}