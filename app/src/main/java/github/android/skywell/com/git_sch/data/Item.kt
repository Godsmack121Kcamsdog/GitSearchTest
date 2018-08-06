package github.android.skywell.com.git_sch.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Item {

    @SerializedName("id")
    @Expose
    var id: Long = 0

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

    override fun toString(): String {
        return "Item{" +
                "id:" + id +
                ", name:" + name + '\''.toString() +
                ", url:" + url + '\''.toString() +
                '}'.toString()
    }
}
