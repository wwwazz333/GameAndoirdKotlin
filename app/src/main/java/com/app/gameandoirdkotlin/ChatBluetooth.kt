package com.app.gameandoirdkotlin

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.InputStream
import java.io.OutputStream
import java.util.*


val STATE_LISTENING = 1
val STATE_CONNECTING = 2
val STATE_CONNECTED = 3
val STATE_CONNECTION_FAILED = 4
val STATE_MESSAGE_RECEIVED = 5

var REQUEST_ENABLE_BLUETOOTH = 1

lateinit var listen:android.widget.Button
lateinit var send:android.widget.Button
lateinit var listDevices:android.widget.Button

lateinit var listView: ListView
lateinit var msg_box: TextView
lateinit var status:TextView
lateinit var writeMsg: EditText

lateinit var bluetoothAdapter: BluetoothAdapter
var btArray = mutableListOf<BluetoothDevice>()

private val APP_NAME = "BTChat"
private val MY_UUID: UUID = UUID.fromString("8ce255c0-223a-11e0-ac64-0803450c9a66")

var handler = Handler(object : Handler.Callback {
    override fun handleMessage(msg: Message): Boolean {
        when (msg.what) {
            STATE_LISTENING -> status.text = "Listening"
            STATE_CONNECTING -> status.text = "Connecting"
            STATE_CONNECTED -> status.text = "Connected"
            STATE_CONNECTION_FAILED -> status.text = "Connection Failed"
            STATE_MESSAGE_RECEIVED -> {
                val readBuff = msg.obj as ByteArray
                val tempMsg = String(readBuff, 0, msg.arg1)
                msg_box.text = tempMsg
            }
        }
        return true
    }
})
lateinit var sendReceive: ChatBluetooth.SendReceive
class ChatBluetooth : AppCompatActivity() {







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_bluetooth)

        findViewByIdes()
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if(!bluetoothAdapter.isEnabled){
            var enableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableIntent, REQUEST_ENABLE_BLUETOOTH)
        }

        implementListeners()
    }

    private fun implementListeners() {
        listDevices.setOnClickListener {
            var bt = bluetoothAdapter.bondedDevices
            var strings = mutableListOf<String>()
            btArray.clear()
            btArray.addAll(bt)
            var i = 0
            for (device in btArray){
                strings.add(device.name)
                i++
            }
            var arrayAdapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, strings)
            listView.adapter = arrayAdapter

        }

        listen.setOnClickListener {
            val serverClass = ServerClass()
            serverClass.start()
        }

        listView.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
                val clientClass = ClientClass(btArray[i])
                clientClass.start()
                status.text = "Connecting"
            }

        send.setOnClickListener {
            var string = writeMsg.text.toString()
            sendReceive.write(string.toByteArray())
        }
    }


    private fun findViewByIdes() {
        listen = findViewById(R.id.listen)
        send= findViewById(R.id.send)
        listView= findViewById(R.id.listview)
        msg_box = findViewById(R.id.msg)
        status= findViewById(R.id.status)
        writeMsg= findViewById(R.id.writemsg)
        listDevices = findViewById(R.id.listDevices)
    }
    private class ServerClass() : Thread() {
        private var serverSocket: BluetoothServerSocket? = null

        override fun run() {
            var socket: BluetoothSocket? = null
            while (socket == null) {
                try {
                    val message = Message.obtain()
                    message.what = STATE_CONNECTING
                    handler.sendMessage(message)
                    socket = serverSocket!!.accept()
                } catch (e: Exception) {
                    e.printStackTrace()
                    val message = Message.obtain()
                    message.what = STATE_CONNECTION_FAILED
                    handler.sendMessage(message)
                }
                if (socket != null) {
                    val message = Message.obtain()
                    message.what = STATE_CONNECTED
                    handler.sendMessage(message)

                    var sendReceive = SendReceive(socket)
                    sendReceive.start()


                    break
                }
            }
        }

        init {
            try {
                serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME, MY_UUID)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private class ClientClass(private val device: BluetoothDevice) : Thread() {
        private var socket: BluetoothSocket? = null
        override fun run() {
            try {
                socket!!.connect()
                val message = Message.obtain()
                message.what = STATE_CONNECTED
                handler.sendMessage(message)

                var sendReceive = SendReceive(socket!!)
                sendReceive.start()


            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                val message = Message.obtain()
                message.what = STATE_CONNECTION_FAILED
                handler.sendMessage(message)
            }
        }

        init {
            try {
                socket = device.createRfcommSocketToServiceRecord(MY_UUID)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    class SendReceive(private val bluetoothSocket: BluetoothSocket) : Thread() {
        private val inputStream: InputStream?
        private val outputStream: OutputStream?
        override fun run() {
            val buffer = ByteArray(1024)
            var bytes: Int
            while (true) {
                try {
                    if (inputStream != null) {
                        bytes = inputStream.read(buffer)
                        handler.obtainMessage(STATE_MESSAGE_RECEIVED, bytes, -1, buffer).sendToTarget()
                    }

                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun write(bytes: ByteArray?) {
            try {
                outputStream!!.write(bytes)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

        init {
            var tempIn: InputStream? = null
            var tempOut: OutputStream? = null
            try {
                tempIn = bluetoothSocket.inputStream
                tempOut = bluetoothSocket.outputStream
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            inputStream = tempIn
            outputStream = tempOut
        }
    }
}
