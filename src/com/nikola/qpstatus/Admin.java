package com.nikola.qpstatus;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Admin extends Activity {
	
	private Spinner spnGradovi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin);
		
		spnGradovi = (Spinner) findViewById(R.id.adminGradovi);
		
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Login.listGradova);
		 dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 spnGradovi.setAdapter(dataAdapter);
		 
		
	}

	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.adminSpremi:
				postaviPostavke();
				spremiPostavke();
				onBackPressed();
				break;
			case R.id.adminOdbaci:
				onBackPressed();
				break;
	
			default:
				break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.admin, menu);
		return true;
	}
	
	private void postaviPostavke(){
		Login.grad = spnGradovi.getSelectedItem().toString();
	}

	private void spremiPostavke(){
		getSharedPreferences(Konstante.PREFS_NAME_OPTIONS, MODE_PRIVATE)
        .edit()
        .putString(Konstante.PREF_GRAD, Login.grad)
        .commit();
	}
	
}
