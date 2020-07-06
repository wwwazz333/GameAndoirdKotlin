package com.app.gameandoirdkotlin

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.net.wifi.p2p.WifiP2pDevice
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast

class BluetoothActivity : AppCompatActivity() {

    lateinit var buttonOnOff:Button
    lateinit var buttonShow:Button
    lateinit var listView:ListView

    lateinit var myBluetoothAdapter: BluetoothAdapter
    lateinit var btnEnablingIntent: Intent
    var requestCodeForEnable = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)

        buttonOnOff = findViewById(R.id.btnOnOff)
        buttonShow = findViewById(R.id.btnShow)
        listView = findViewById(R.id.peers)
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        btnEnablingIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)

        bluetoothMethod()

        exeButton()
    }

    private fun exeButton() {
        buttonShow.setOnClickListener {
            var bt:Set<BluetoothDevice> = myBluetoothAdapter.bondedDevices
            var peers = mutableListOf<String>()
            var i = 0
            if(bt.isNotEmpty()){
                for(device in bt){
                    peers.add(device.name)
                    i++
                }
                var arrayAdapter = ArrayAdapter<String>(applicationContext,android.R.layout.simple_list_item_1,peers)
                listView.adapter = arrayAdapter
            }
        }
    }

    private fun bluetoothMethod() {
        buttonOnOff.setOnClickListener {
            if(myBluetoothAdapter.isEnabled){
                myBluetoothAdapter.disable()
                buttonOnOff.text = "OFF"
            }
            if(myBluetoothAdapter==null){
                Toast.makeText(applicationContext, "not supported on this device", Toast.LENGTH_SHORT).show()
                buttonOnOff.text = "OFF"
            }else{
                if(!myBluetoothAdapter.isEnabled){
                    startActivityForResult(btnEnablingIntent, requestCodeForEnable)
                    buttonOnOff.text = "ON"
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == requestCodeForEnable){
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(applicationContext,"Bluetooth is enabled",Toast.LENGTH_SHORT).show()
            }else if(resultCode == RESULT_CANCELED){
                Toast.makeText(applicationContext,"Bluetooth enabling canclled",Toast.LENGTH_SHORT).show()
            }
        }
    }
}