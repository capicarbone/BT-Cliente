package personal.bt.client;


import personal.bt.client.threads.ConnectThread;
import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class ConexionClienteAct extends Activity {
	
	private BluetoothAdapter btAdapter;
	private static final int PETICION_ACTION_BT = 45;
	
	private ConnectThread hiloCliente;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conexion_cliente);
		
		btAdapter = BluetoothAdapter.getDefaultAdapter();
				
		TextView respuesta = (TextView) findViewById(R.id.respuesta_cliente);
		
		// Se verifica que se haya encontrado un adaptador
		if (btAdapter != null ){
			
			// Si el adaptador no está activado se solicita su activación
			
			if (!btAdapter.isEnabled()){
				Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, PETICION_ACTION_BT);
			}
												
		}
	}
	
	@Override 
	public void onResume(){
		
		super.onResume();
		
		Intent intent = getIntent();
		BluetoothDevice target = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		
		hiloCliente = new ConnectThread(btAdapter, target);
		//hiloCliente.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_conexion_cliente, menu);
		return true;
	}

}
