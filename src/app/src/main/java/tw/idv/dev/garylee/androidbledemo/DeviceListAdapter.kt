package tw.idv.dev.garylee.androidbledemo

import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.device_list_row.view.*
import java.util.*


class DeviceListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.device_list_row, parent, false)
        return Item(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Item).bind(
                position.toString(),
                uuid=UUID.randomUUID(),
                connected=((position % 2) == 0),
                notes=listOf("new", "bind", "beacon", "power")
        )
    }

    class Item(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun getIconText(connected: Boolean) = itemView.context.getString(if (connected) R.string.mdi_bluetooth_connect else R.string.mdi_bluetooth)

        fun bind(name: String, uuid: UUID, connected: Boolean, notes: List<String>) {
            if (connected) {
                itemView.icon.setTextColor(itemView.context.getColor(R.color.bleNoteInfo))
            }
            itemView.icon.text = getIconText(connected)
            itemView.deviceName.text = name
            itemView.deviceUuid.text = uuid.toString()
            for (i in 0 until 4) {
                itemView.note0.text = if (i < notes.count()) notes[i] else ""
            }
        }
    }
}