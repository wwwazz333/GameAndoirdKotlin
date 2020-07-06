package com.app.gameandoirdkotlin

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.core.app.ActivityCompat

class ConnectWifiP2pActivity : AppCompatActivity() {

    lateinit var btnDiscover:android.widget.Button
    lateinit var btnSend:android.widget.Button

    lateinit var read_msg_box:TextView
    lateinit var connectionStatus:TextView

    lateinit var listView:ListView

    lateinit var writeMsg:EditText

    lateinit var wifiManager: WifiManager
    lateinit var mManager:WifiP2pManager
    lateinit var mChannel:WifiP2pManager.Channel

    lateinit var mReciver: BroadcastReceiver
    lateinit var mIntentFilter: IntentFilter

    var peers = mutableListOf<WifiP2pDevice>()
    var deviceNameArray = mutableListOf<String>()
    var deviceArray = mutableListOf<WifiP2pDevice>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_wifi_p2p)
        initialWork()
        exqListener()
    }


    @SuppressLint("MissingPermission")
    private fun exqListener() {
        btnDiscover.setOnClickListener {
            mManager.discoverPeers(mChannel, object : WifiP2pManager.ActionListener{
                override fun onSuccess() {
                    connectionStatus.text = "Discovery Started"
                }

                override fun onFailure(reason: Int) {
                    connectionStatus.text = "Discovery Starting Failed"
                }
            })
        }
    }


    private fun initialWork() {
        btnDiscover = findViewById(R.id.discover)
        btnSend = findViewById(R.id.sendButton)

        read_msg_box = findViewById(R.id.readMsg)
        connectionStatus = findViewById(R.id.connectionStatus)

        listView = findViewById(R.id.peerListView)
        writeMsg = findViewById(R.id.writeMsg)

        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        mManager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
        mChannel = mManager.initialize(this, Looper.getMainLooper(), null)

        mReciver =  WifiDirectBroadcastReciver(mManager, mChannel, this)

        mIntentFilter = IntentFilter()
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)



    }
    var peerListener= object : WifiP2pManager.PeerListListener {
        override fun onPeersAvailable(peerList: WifiP2pDeviceList?) {
            if(!peerList!!.deviceList.equals(peers)){
                peers.clear()
                peers.addAll(peerList!!.deviceList)

                var i = 0
                for(device in peerList.deviceList){
                    deviceNameArray[i] = device.deviceName
                    deviceArray[i] = device
                    i++
                }

                var adapter = ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,deviceNameArray)
                listView.adapter = adapter
            }
            if(peers.isEmpty()){
                Log.d("TAG", "No devices found")
                return

            }
        }
    }



    override fun onResume() {
        super.onResume()
        mReciver =  WifiDirectBroadcastReciver(mManager, mChannel, this)
        registerReceiver(mReciver, mIntentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mReciver)
    }
}