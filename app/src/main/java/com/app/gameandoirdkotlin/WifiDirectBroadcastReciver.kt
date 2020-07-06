package com.app.gameandoirdkotlin

import android.Manifest
import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.wifi.p2p.WifiP2pManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat

class WifiDirectBroadcastReciver(mManager: WifiP2pManager, mChannel:WifiP2pManager.Channel, mActivity: ConnectWifiP2pActivity) : BroadcastReceiver() {

    private var mManager: WifiP2pManager = mManager
    private var mChannel:WifiP2pManager.Channel = mChannel
    private var mActivity:ConnectWifiP2pActivity = mActivity

    override fun onReceive(context: Context?, intent: Intent?) {
        var action: String = intent!!.action.toString()
        when(intent.action){
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION ->{
                var state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)

                if(state == WifiP2pManager.WIFI_P2P_STATE_ENABLED){
                    Toast.makeText(context, "wifi is on", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "wifi is off", Toast.LENGTH_SHORT).show()
                }
            }
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION ->{
                if(mManager!=null){
                    if (ActivityCompat.checkSelfPermission(
                            mActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return
                    }
                    mManager.requestPeers(mChannel,mActivity.peerListener)
                }

            }
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION ->{

            }
            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION ->{

            }
        }
    }
}