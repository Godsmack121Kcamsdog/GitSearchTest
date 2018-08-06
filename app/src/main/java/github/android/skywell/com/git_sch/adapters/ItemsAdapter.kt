package github.android.skywell.com.git_sch.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList

import github.android.skywell.com.git_sch.data.Item
import github.android.skywell.com.sky_git_searcher.R

class ItemsAdapter(context: Context) : RecyclerView.Adapter<ItemsAdapter.SimpleHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val contentList: MutableList<Item>

    init {
        contentList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleHolder {
        return SimpleHolder(inflater.inflate(R.layout.rep_item, parent, false))
    }

    override fun onBindViewHolder(holder: SimpleHolder, position: Int) {
        holder.name.text = contentList[position].name
        holder.id.text = contentList[position].id.toString()
        holder.url.text = contentList[position].url
    }

    override fun getItemCount(): Int {
        return contentList.size
    }

    fun updateContent(contentList: List<Item>) {
        this.contentList.clear()
        this.contentList.addAll(contentList)
        notifyDataSetChanged()
    }

    inner class SimpleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView = itemView.findViewById(R.id.name)
        var id: TextView = itemView.findViewById(R.id.id)
        var url: TextView = itemView.findViewById(R.id.url)

    }
}
