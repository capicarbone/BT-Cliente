package personal.bt.client.threads;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class ConnectThread extends Thread{
	
	private BluetoothAdapter adapterBt;
	private BluetoothDevice deviceTarget;
	
	private final BluetoothSocket socket;
	
	private final UUID UUID_APP;
	private final String UUID_STR = "f170ff30-421f-11e2-a25f-0800200c9a66";
	
	public ConnectThread(BluetoothAdapter adp, BluetoothDevice device){
		
		 BluetoothSocket tmp = null;
		 deviceTarget = device;
		 UUID_APP = UUID.fromString(UUID_STR);
	 	        
        /*try {
            
            tmp = device.createRfcommSocketToServiceRecord(UUID_APP);
        } catch (IOException e) { } */
         socket = tmp;
		
	}
	
	public void run(){
		
		
        adapterBt.cancelDiscovery();
 
        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            socket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            try {
                socket.close();
            } catch (IOException closeException) { }
            return;
        }
		
	}

}
