package com.nikola.qpstatus;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Test extends Procesing {

private Spinner spinner1;
private Spinner spinner2;
private Spinner spinner3;
private Spinner spinner4;
private Spinner spinner5;
private static ArrayList<String> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        TextView tv = (TextView) findViewById(R.id.textViewTest1);
        //int i = Login.testTemp[0].length-2;
        //tv.append(Login.testTemp[0][i]);
//        tv.append("\n------\n" + Login.grad);
//        tv.append("\n------\n" + Login.ime);
//        tv.append("\n------\n" + Login.prezime);
//        tv.append("\n------\n" + String.valueOf(Login.loggedIn));
//        tv.append("\n------\n" + String.valueOf(Login.superAdmin));
		//tv.append(Login.eventsSplit[1]);
		//tv.append(String.valueOf(Login.eventsSplit.length));
        
        String[] polja = {"90", "80", "70", "60", "50", "40", "30", "20", "10", "0"};
        list = Tools.popuniSpinner(polja);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		 dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(dataAdapter);
		
        spinner3 = (Spinner) findViewById(R.id.spinner2);
		spinner3.setAdapter(dataAdapter);
		
		spinner4 = (Spinner) findViewById(R.id.spinner3);
		spinner4.setAdapter(dataAdapter);
		
		String[] polja1 = {"1", "5", "10", "20", "50"};
        list = Tools.popuniSpinner(polja1);
		spinner5 = (Spinner) findViewById(R.id.spinner4);
		dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		 dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner5.setAdapter(dataAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }
    
    public static String vadiPolja(){
    	
    	return null;
    }
}
