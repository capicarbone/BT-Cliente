package personal.bt.client;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class ConexionesDisponiblesAct extends Activity {
	
	private static final int PETICION_ACTION_BT = 45;
	private static final String DEVICE_TARGET = "personal.bt.client.DEVICE_TARGET";
	
	
	private ArrayAdapter<BluetoothDevice> disponiblesList;
	private BluetoothAdapter btAdapter;
	
	// Receivers
	
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		
	    public void onReceive(Context context, Intent intent) {
	    	
	        String action = intent.getAction();
	        	        
	        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
	           
	            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	            	            	           
	            disponiblesList.add(device);	            	           	            
	            
	        }
	    }
	};
	
	// Eventos
	
	private OnItemClickListener conectarDispositivo = new OnItemClickListener() {
	    public void onItemClick(AdapterView parent, View v, int position, long id) {
	    	
	    	ConexionesDisponiblesAct context = (ConexionesDisponiblesAct) parent.getContext(); 
	    	
	    	Intent intent = new Intent(context, ConexionClienteAct.class);
	    	intent.putExtra(DEVICE_TARGET, disponiblesList.getItem(position));
	    	startActivity(intent);
	    	
	        	        
	    }
	};
	
	// Métodos del ciclo de vida del Activity

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		// Se asigna su correspondiente layout al Activity
		setContentView(R.layout.activity_conexiones_disponibles);
		
		
		// Se obtiene del layout la lista para los dispositivos y se le es asignado un
		// evento para recibirlos
		
		ListView lista = (ListView) findViewById(R.id.dispositivos_disponibles);
		
		lista.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> padre, View vista, int posicion, long id) {
					
				BluetoothDevice device = disponiblesList.getItem(posicion);
				Intent intent = new Intent(vista.getContext(), ConexionClienteAct.class);
				intent.putExtra(BluetoothDevice.EXTRA_DEVICE, device);
				startActivity(intent);
								
			}
		});
		
		// Se obtiene el adaptador Bluetooth del dispositivo
		
		btAdapter = BluetoothAdapter.getDefaultAdapter();
				
		// Se verifica que se haya encontrado un adaptador
		if (btAdapter != null ){
			
			// Si el adaptador no está activado se solicita su activación
			
			if (!btAdapter.isEnabled()){
				Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, PETICION_ACTION_BT);
			}
			
			// Se muestran los dispositivos
			
			mostrarDispositivos();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_conexiones_disponibles, menu);
		return true;
	}
	
	// Métodos manejadores de respuestas
	
	
	
	// Métodos de separación de la lógica
	
	private void mostrarDispositivos(){
		
		// Se obtiene el ListView del Activity y se inicializar el adaptador para el control de los datos
		
		ListView lista = (ListView) findViewById(R.id.dispositivos_disponibles);
		disponiblesList = new ArrayAdapter<BluetoothDevice>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
		
		// Se asigana el adaptador a la lista.
		
		lista.setAdapter(disponiblesList);
		
		// Se asigna el Receiver para controlar la detección de dispositivos
		
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter);
		
		// Se inicia la deteccion de dispositivos, dura 12 seg de forma asíncrona.
		
		btAdapter.startDiscovery();
		
	}
	
}
