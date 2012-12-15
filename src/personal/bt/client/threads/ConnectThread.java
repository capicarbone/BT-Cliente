package personal.bt.client.threads;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.widget.TextView;

public class ConnectThread extends Thread{
	
	private BluetoothAdapter adapterBt;
	private BluetoothDevice deviceTarget;
	
	private final BluetoothSocket socket;
	
	private final UUID UUID_APP;
	private final String UUID_STR = "f170ff30-421f-11e2-a25f-0800200c9a66";
	private TextView respuestaView;
	
	public ConnectThread(BluetoothAdapter adp, BluetoothDevice device, TextView view){
		
		 BluetoothSocket tmp = null;
		 deviceTarget = device;
		 UUID_APP = UUID.fromString(UUID_STR);
		 adapterBt = adp;
		 
		 respuestaView = view;
	 	        
        try {
                    	
            tmp = device.createRfcommSocketToServiceRecord(UUID_APP);
        } catch (IOException e) { } 
         socket = tmp;
		
	}
	
	public void enviarMensaje( String mensaje){
		
		OutputStream output = null;
		byte[] buffer = mensaje.getBytes();		
		
		try {
			output = socket.getOutputStream();
		} catch (IOException e) {}
		
		try {
			output.write(buffer);
		} catch (IOException e) {	}
		
	}
	
	public String recibirMensaje(){
		
		
		InputStream input = null;
		byte[] buffer = new byte[1024];
		String respuesta = null;
		
		try {
			input = socket.getInputStream();
		} catch (IOException e) {
			
			respuesta = "No se logró obtener el stram.";
			
		}
		
		try {
			input.read(buffer);
			respuesta = new String(buffer);
		} catch (IOException e) {
			respuesta = "No se logró realizar la lectura";
		}
		
		
		
		return respuesta;
		
		
	}
	
	public void run(){
		
		final String respuesta;
        adapterBt.cancelDiscovery();
 
        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception        	
            socket.connect();
        } catch (IOException connectException) {        	        	        
            // Unable to connect; close the socket and get out
        	cancelar();
            return;
        }
        
        enviarMensaje("Hola Carlos");
        respuesta = recibirMensaje();
        
        respuestaView.post(new Runnable() {
			
			public void run() {
				respuestaView.setText(respuesta);
				
			}
		});
        
        cancelar();
                	
	}
	
	public void cancelar(){
		 try {
             socket.close();
         } catch (IOException closeException) { }
	}

}
