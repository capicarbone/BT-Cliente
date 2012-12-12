package personal.bt.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainAct extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	
	// Eventos sobre Views
	
	public void mostrarDispositivosDisponibles(View view){
		Intent conexionesDisponibles = new Intent(this, ConexionesDisponiblesAct.class);
		startActivity(conexionesDisponibles);
	}

}
