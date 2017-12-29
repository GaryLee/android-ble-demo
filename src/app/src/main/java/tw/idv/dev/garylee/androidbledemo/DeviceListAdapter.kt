package tw.idv.dev.garylee.androidbledemo

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.device_list_row.*

/**
 * Created by garylee on 2017/12/30.
 */
class DeviceListAdapter(var context: Context): BaseAdapter() {
    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val inflate = inflater.inflate(R.layout.device_list_row, null)
        return inflate
    }
}