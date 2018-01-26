package tw.idv.dev.garylee.androidbledemo

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.device_list_row.view.*
import java.util.*

data class BleDeviceItem(
        val name: String,
        val address: String,
        val type: Int,
        val rssi: Double
)

class DeviceListAdapter(val bluetoothManager: BluetoothManager): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val deviceItemList = mutableListOf<BleDeviceItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.device_list_row, parent, false)
        return Item(view)
    }

    override fun getItemCount() = deviceItemList.count()

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

/**
 * Start to scan devices.
 */
fun DeviceListAdapter.startScanDevices(): Boolean {
    val bluetoothAdapter = bluetoothManager.adapter
    if (bluetoothAdapter == null) {
        Log.d("startScanDevices", "bluetoothAdapter is null")
        return false
    }
    if (!bluetoothAdapter.enable()) {
        if (!bluetoothAdapter.isEnabled()) {
            Log.d("startScanDevices", "bluetoothAdapter is not enabled.")
            return false
        }
    }
    val scanCallback = object:ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            // super.onScanResult(callbackType, result)
            if (result == null || result.device == null) {
                Log.d("startScanDevices", "result is null or result.device is null.")
                return
            }
            val deviceName = if (result.device.name == null) "Noname" else result.device.name
            Log.d("startScanDevices", "onScanResult(): ${result.device.address} - ${deviceName}")
            deviceItemList.add(BleDeviceItem(deviceName, result.device.address, 0, 0.0))
            notifyDataSetChanged()
        }
    }
    bluetoothAdapter.bluetoothLeScanner.startScan(scanCallback)
    return true
}
