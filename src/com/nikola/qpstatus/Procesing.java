package com.nikola.qpstatus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import my_class.EventsArrayAdapter;
import my_class.MyPerformanceArrayAdapter;

import org.joda.time.DateTime;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class Procesing extends MyBaseActivity {

	private static Object events;
	private static boolean serverStatus;
	private static Double ukupno = 0d;
	private static String grad;
	private static boolean ima;

	private static int uspjeleTrans;
	private static int ukupnoTrans;
	private static float postotakTrans;

	private static int standardEvents;
	private static int mediumRiskEvents;
	private static int highRiskEvents;
	private static String[] highRisk = { "UNAUTH", "MDO", "AUTH", "PDO",
			"PRNerrRSP" };
	private static String[] standardRisk = { "STU", "RBT", "CLS" };
	private static String[] poljaTestTT = { "DEVICE_ID", "TIME", "PRICE",
			"TICKET_NO", "REG", "ZONE", "PAN", "APPROVAL_CODE",
			"ISSUER_NAME", "PERIOD_SINCE", "PERIOD_TO", /**
			 * 
			 * 
			 * "TICKET"
			 */
			"STATUS", "RESPONSE_CODE" };
	private static String[] poljaTestEvents = { "TIME", "DEVICE", "MESSAGE",
			"TYPE" };

	private static DateTime dtTo = null;
	private static DatePickerFragment toFragment;

	private static DateTime dtSince = null;
	private static DatePickerFragment sinceFragment;

	private Button to;
	private Button since;
	private ImageView iv;
	private ImageView ivServer;
	private TextView verzijaApp;
	private Context context = Procesing.this;
	private TextView txtUkupno;
	
	private ArrayList<String> listRows;
	private ListView listView;
	private List<Map<String, String>> listOfMaps;
	private static int clickPosition = -1;

	private static Spinner spinner1;
	public static String sinceTime;
	public static String toTime;
	public static String[] eventsSplit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_procesing);

		inicijalizacija();
		serverDialog();
		eventsDialog();
		eventsExe();
		transExe();
		vrijemeExe();
		
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Login.grad = spinner1.getSelectedItem().toString();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		if (Login.eventFlag == Login.EventType.events) {
			new soapTaskEventsTest().execute(poljaTestEvents);
		} else if (Login.eventFlag == Login.EventType.trans) {
			new soapTaskTransTest().execute(poljaTestTT);
		} else {

		}

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showDialodWithTrans(listOfMaps.get(position), Procesing.this);
				clickPosition = position;
			}
		});	
	}

//	@Override
//	protected void onPause() {
//		logOff();
//		super.onPause();
//	};
	
	@Override
	protected void onStop() {
		logOff();
		super.onStop();
	}
	
	@Override
	public void onBackPressed() {
		logOff();
		super.onBackPressed();
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
//		if(!Login.loggedIn){
//			Intent intent = new Intent(Procesing.this, Main.class);
//			startActivity(intent);
//		}
	}
	
	private void logOff() {
		Login.loggedIn = false;
		Login.superAdmin = false;
		Login.eventFlag = Login.EventType.nothing;
	}
	
	static class ViewHolderTrans extends MyPerformanceArrayAdapter.ViewHolder {
		public TextView odV;
		public TextView doV;
		public TextView status;
		public TextView registracija;
	}
	
	private View buildTransItemDialog(Map<String, String> map, Activity context){
		LayoutInflater inflater =  context.getLayoutInflater();
		View lila = inflater.inflate(R.layout.trans_item, null);
		ViewHolderTrans holder = new ViewHolderTrans();
		holder.okImage = (ImageView) lila.findViewById(R.id.transStatusIv);
		holder.printImage = (ImageView) lila.findViewById(R.id.procOnlineIv);
		holder.cardImage = (ImageView) lila.findViewById(R.id.transCardIv);
		holder.text = (TextView) lila.findViewById(R.id.transTextTxt);
		holder.doV = (TextView) lila.findViewById(R.id.transVrijediDoTxt);
		holder.odV = (TextView) lila.findViewById(R.id.transTimeTxt);
		holder.status = (TextView) lila.findViewById(R.id.transCodeTxt);
		holder.registracija = (TextView) lila.findViewById(R.id.transDeviceTxt);
		MyPerformanceArrayAdapter.iconsSet(holder, map);
		holder.doV.setText(map.get("PERIOD_TO"));
		holder.odV.setText(map.get("PERIOD_SINCE"));
		if(!map.get("REG").equals("n/a"))
			holder.registracija.setText(map.get("REG"));
		else
			holder.registracija.setVisibility(View.GONE);
		if(map.get("FLAG").equals("00"))
			holder.status.setText("OK (00)");
		else
			holder.status.setText("Error (" + map.get("FLAG") + ")");
		lila.setTag(holder);
		return lila;
	}
	
	private void showDialodWithTrans(Map<String, String> map, Activity context){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setTitle("O nama");
		if(Login.eventFlag == Login.EventType.trans){
			alertDialogBuilder.setTitle("Transakcija " + map.get("TICKET_NO"))
						.setView(buildTransItemDialog(map, context));
		}
		else {
			alertDialogBuilder.setTitle("Događaj " + map.get("FLAG"))
						.setView(buildEventsItemDialog(map, context));
		}
		alertDialogBuilder
				.setIcon(R.drawable.transakcije).setCancelable(true)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						clickPosition = -1;
					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
	static class ViewHolderEvents extends EventsArrayAdapter.ViewHolder{
		public TextView txtVrijeme;
		public TextView txtSerial;
		public TextView txtCode;
	}
	
	private View buildEventsItemDialog(Map<String, String> map, Activity context){
		LayoutInflater inflater =  context.getLayoutInflater();
		View lila = inflater.inflate(R.layout.events_item, null);
		ViewHolderEvents holder = new ViewHolderEvents();
		holder.okImage = (ImageView) lila.findViewById(R.id.eventsOkIv);
		holder.text = (TextView) lila.findViewById(R.id.eventsTextTxt);
		holder.txtSerial = (TextView) lila.findViewById(R.id.eventsSerialTxt);
		holder.txtCode = (TextView) lila.findViewById(R.id.eventsCodeTxt);
		holder.txtVrijeme = (TextView) lila.findViewById(R.id.eventsTimeTxt);
		
		EventsArrayAdapter.setHeader(holder, map);
		
		holder.txtVrijeme.setText(map.get("TIME"));
		holder.txtCode.setText(map.get("FLAG"));
		holder.txtSerial.setText(map.get("DEVICE"));
		
		lila.setTag(holder);
		return lila;
	}
	
	private void eventsDialog() {
		iv.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String poruka = null;
				int ikona = 0;
				if (Login.eventFlag == Login.EventType.trans) {
					ikona = R.drawable.transakcije;
					poruka = "U zadanom periodu ima "
							+ String.valueOf(uspjeleTrans) + "/"
							+ String.valueOf(ukupnoTrans)
							+ " uspješnih transakcija.\n"
							+ "Ostvareni prihod je: " + ukupno + "0HRK";

				} else if (Login.eventFlag == Login.EventType.events) {
					ikona = R.drawable.events;
					poruka = "U zadanom periodu ima: \n"
							+ "-rizičnih događaja: "
							+ String.valueOf(highRiskEvents) + "\n"
							+ "-srednje rizičnih događaja: "
							+ String.valueOf(mediumRiskEvents) + "\n"
							+ "-standardnih događaja: "
							+ String.valueOf(standardEvents);
				} else {
					ikona = R.drawable.report;
					poruka = "Niste izvršili niti jedan zahtjev za događajima.";
				}
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);
				alertDialogBuilder.setTitle("Stanje...");
				alertDialogBuilder
						.setMessage(poruka)
						.setIcon(ikona)
						.setCancelable(true)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {

									}
								});

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		});
	}

	/**
	 * 
	 */
	private void serverDialog() {
		Thread checkServer = new Thread(new Runnable() {
			boolean online = false;
			@Override
			public void run() {
				online = Tools.isOnline(Procesing.this);
				serverStatus = Tools.checkServer();
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						ivServer.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								String poruka;
								if (online) {
									if (serverStatus) {
										poruka = "Sa serverom je sve u redu i komunicira sa uređajem.";
										ivServer.setImageResource(R.drawable.online);
									} else {
										poruka = "Uredaj se ne moze povezati na server.";
										ivServer.setImageResource(R.drawable.offline);
									}
								} else {
									poruka = "Uredaj se ne moze povezati na server jer se je dogodila greška u komunikaciji.";
									ivServer.setImageResource(R.drawable.offline);
								}

								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
										context);
								alertDialogBuilder.setTitle("Server status...");
								alertDialogBuilder
										.setMessage(poruka)
										.setIcon(R.drawable.server)
										.setCancelable(true)
										.setPositiveButton("OK",
												new DialogInterface.OnClickListener() {
													public void onClick(DialogInterface dialog,
															int id) {}
												});

								AlertDialog alertDialog = alertDialogBuilder.create();
								alertDialog.show();
							}
						});
					}
				});
			}
		});
		checkServer.start();
	}

	private void loginInfoDialog() {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setTitle("Inoformacije o korisniku");
		alertDialogBuilder
				.setMessage(
						Login.vratiUserLogin() + "\n" + Login.vratiMaticne())
				.setIcon(R.drawable.info).setCancelable(true)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	private void aboutDialog() {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setTitle("O nama");
		alertDialogBuilder
				.setMessage(
						"Međimurje IPC d.d.\n" + Login.verzijaApp)
				.setIcon(R.drawable.qp128).setCancelable(true)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	private void vrijemeExe() {
		to = (Button) findViewById(R.id.toButton);
		to.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Bundle args = new Bundle();
				args.putInt("textView", R.id.toButton);
				toFragment = new DatePickerFragment();
				toFragment.setArguments(args);
				toFragment.show(getFragmentManager(), "datePicker");
			}
			
		});
		since = (Button) findViewById(R.id.sinceButton);
		since.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Bundle args = new Bundle();
				args.putInt("textView", R.id.sinceButton);
				sinceFragment = new DatePickerFragment();
				sinceFragment.setArguments(args);
				sinceFragment.show(getFragmentManager(), "datePicker");
			    
			}
		});
		inicijalizirajVrijeme();
	}

	private void transExe() {
		Button b2 = (Button) findViewById(R.id.transButton);
		b2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				inicijalizirajVrijeme();
				grad = spinner1.getSelectedItem().toString();
				ukupno = 0d;
				uspjeleTrans = 0;
				ukupnoTrans = 0;
				standardEvents = 0;
				mediumRiskEvents = 0;
				highRiskEvents = 0;
				if (Tools.isOnline(Procesing.this) == false) {
					Tools.internetDialog(context, Procesing.this);
				} else if (serverStatus == false) {
					Toast.makeText(getApplicationContext(),
							"Problemi sa serverom!!", Toast.LENGTH_SHORT)
							.show();
				} else {
					Login.eventFlag = Login.EventType.trans;
					new soapTaskTransTest().execute(poljaTestTT);
				}
			}
		});
	}

	/** inicijalizacija pocetnih parametara */

	private void inicijalizacija() {
		napraviSpinerGrad();
		grad = Login.grad;
		listView = (ListView) findViewById(R.id.procListView);
		verzijaApp = (TextView) findViewById(R.id.textView4);
		verzijaApp.setText(Login.verzijaApp);
		iv = (ImageView) findViewById(R.id.row_icon);
		ivServer = (ImageView) findViewById(R.id.procOnlineIv);
		txtUkupno = (TextView) findViewById(R.id.procUkupnotxt);
		new Thread(new Runnable() {
			@Override
			public void run() {
				serverStatus = Tools.checkServer();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (serverStatus == true) {
							ivServer.setImageResource(R.drawable.online);
						} else {
							ivServer.setImageResource(R.drawable.offline);
						}
					}
				});
			}
		}).start();
	}


	private void updateDisplayTo() {
		if(toFragment != null && toFragment.getSelectedDate() != null)
			dtTo = toFragment.getSelectedDate();
		to.setText(dtTo.toString("dd.MM.yyyy"));
		toTime = dtTo.toString();
	}

	private void updateDisplaySince() {
		if(sinceFragment != null && sinceFragment.getSelectedDate() != null)
			dtSince = sinceFragment.getSelectedDate();
		since.setText(dtSince.toString("dd.MM.yyyy"));
		sinceTime = dtSince.toString();
	}

	private void soapZahtjev(String grad) {
		events = SoapService.getEvent2(0, "", grad, "", 
				sinceTime, toTime, "", "", "", "");
	}

	private void transZahtjev(final String grad) {
		events = SoapService.getTransaction(0, "", grad, "", 
				sinceTime, toTime, "", "", "", "");
	}

	private void soapProcisti() {
		if (events.toString().indexOf("msdata:rowOrder=\"0\"") != -1) {
			String eventsProcisceni = Tools.procistiSetXML(events.toString());
			eventsSplit = eventsProcisceni.toString().split("msdata:");
			ima = true;
		} else {
			ima = false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_procesing, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.izlaz:
			this.finish();
			break;
		case R.id.test:
			final Intent intent = new Intent(this, StatusQP.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			break;
		case R.id.login:
			loginInfoDialog();
			break;
		case R.id.about:
			aboutDialog();
			break;
		}
		return true;
	}

	void inicijalizirajVrijeme() {
		if(dtTo == null){
			dtTo = new DateTime();
		}
		updateDisplayTo();
		if(dtSince == null) {
			dtSince = new DateTime();
		}
		updateDisplaySince();
		if(dtSince.isAfter(dtTo)){
			toTime = dtSince.toString();
			sinceTime = dtTo.toString();
		}
	}

	private void dodajUkupno() {
		ImageView iv = (ImageView) findViewById(R.id.row_icon);
		iv.setImageResource(R.drawable.report);
		txtUkupno.setText(String.valueOf(ukupno) + "0 HRK");
		if (postotakTrans > Login.postoTransUp) {
			iv.setImageResource(R.drawable.green);
		} else if (postotakTrans > Login.postoTransDown) {
			iv.setImageResource(R.drawable.orange);
		} else {
			iv.setImageResource(R.drawable.red);
		}
	}



	/** Funkcija koja postavlja status za dogadaje */
	private void stanjeDogadaji() {
		txtUkupno.setText(eventsSplit.length - 1 + " događaja");
		mediumRiskEvents = eventsSplit.length
				- (highRiskEvents + standardEvents + 1);
		float postotakMedium = ((float) mediumRiskEvents)
				/ (float) (eventsSplit.length - 1);
		if (postotakMedium >= 0.3f) {
			iv.setImageResource(R.drawable.orange);
		}
		if (highRiskEvents > 0 || postotakMedium > 0.55f) {
			iv.setImageResource(R.drawable.red);
		}
		if (highRiskEvents == 0 && postotakMedium < 0.3f) {
			iv.setImageResource(R.drawable.green);
		}
	}

	/** API11 znacajke! */
	//U Bundle treba staviti int id od Button polja
	public static class DatePickerFragment extends android.app.DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		private DateTime datum = null;
		private Button selectTime;

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}
		
		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			datum = new DateTime(year, month + 1, day, 0, 0);
			selectTime = (Button) getActivity().findViewById(getArguments().getInt("textView"));
			selectTime.setText(getSelectedDate("dd.MM.yyyy"));
		}
		
		public DateTime getSelectedDate(){
			return datum;
		}
		
		public String getSelectedDate(String format){
			return datum.toString(format);
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		setContentView(R.layout.activity_procesing);

		inicijalizacija();
		serverDialog();
		eventsDialog();
		eventsExe();
		transExe();
		vrijemeExe();

		if (Login.eventFlag == Login.EventType.events) {
			new soapTaskEventsTest().execute(poljaTestEvents);
		} else if (Login.eventFlag == Login.EventType.trans) {
			new soapTaskTransTest().execute(poljaTestTT);
		} else {

		}
	}

	/** AsyncTask sekcija */

	private class soapTaskTransTest extends AsyncTask<String[], TableRow, Void> {

		private final ProgressDialog dialog = new ProgressDialog(Procesing.this);
		private MyPerformanceArrayAdapter adapter;

		protected void onPreExecute() {
			this.dialog.setMessage("Molim pricekajte...");
			this.dialog.show();
		}

		protected Void doInBackground(String[]... polja) {
			try {
				transZahtjev(grad);
				soapProcisti();
				testVadiPolja(eventsSplit, polja[0]);
				listRows = new ArrayList<String>();
				for (int j = 0; j < Login.testTemp.length; j++) {
					listRows.add(Login.testTemp[j][2]);
				}
				if(adapter != null)
					adapter.clear();
				adapter = new MyPerformanceArrayAdapter(Procesing.this,
				        android.R.layout.simple_list_item_1, listRows, listOfMaps);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(Void result) {
			if (this.dialog.isShowing()) {
				this.dialog.dismiss();
			}
			if (ima) {
				listView.setAdapter(adapter);
				dodajUkupno();
				if(clickPosition != -1)
					listView.performItemClick(listView, clickPosition, listView.getItemIdAtPosition(clickPosition));
			} else {
				clickPosition = -1;
				if (adapter != null) {
					adapter.clear();
					listView.setAdapter(adapter);
				}
				txtUkupno.setText("0.00 HRK");
				iv.setImageResource(R.drawable.orange);
				Toast.makeText(getApplicationContext(),
						"Nema transakcija u zadanom periodu!",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private class soapTaskEventsTest extends AsyncTask<String[], TableRow, Void> {

		private final ProgressDialog dialog = new ProgressDialog(Procesing.this);
		private Animation anim;
		private List<String> listEvents;
		private ArrayAdapter<String> adapter;
		
		protected void onPreExecute() {
			this.dialog.setMessage("Molim pricekajte...");
			this.dialog.show();
		}

		protected Void doInBackground(String[]... polja) {
			listOfMaps = new ArrayList<Map<String, String>>();
			anim = AnimationUtils.loadAnimation(
                    Procesing.this, R.anim.anim_up);
			soapZahtjev(grad);
			soapProcisti();
			testVadiPolja(eventsSplit, polja[0]);
			listEvents = new ArrayList<String>();
			for (int j = 0; j < listOfMaps.size(); j++) {
				listEvents.add(listOfMaps.get(j).get("MESSAGE"));
			}
			adapter = new EventsArrayAdapter(Procesing.this, android.R.layout.simple_list_item_1, 
					listEvents, listOfMaps);
			return null;
		}

		protected void onProgressUpdate(TableRow... values) {
			if (this.dialog.isShowing()) {
				this.dialog.dismiss();
			}
			values[0].startAnimation(anim);
		}
		
		protected void onPostExecute(Void result) {
			if (this.dialog.isShowing()) {
				this.dialog.dismiss();
			}
			if (ima == true) {
				stanjeDogadaji();
				listView.setAdapter(adapter);
				if(clickPosition != -1)
					listView.performItemClick(listView, clickPosition, listView.getItemIdAtPosition(clickPosition));
			} else {
				clickPosition = -1;
				txtUkupno.setText("0 događaja");
				adapter.clear();
				listView.setAdapter(adapter);
				iv.setImageResource(R.drawable.orange);
				Toast.makeText(getApplicationContext(),
						"Nema dogadaja u zadanom periodu!", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
	/** Kraj AsyncTask sekcija */
	
	void testVadiPolja(String[] eventsSplit, String[] poljaTestTT) {
		ukupno = 0d;
		if (ima == true) {
			String temp;
			String temp1 = null;
			Login.testTemp = new String[eventsSplit.length - 1][poljaTestTT.length + 2];
			listOfMaps = new ArrayList<Map<String, String>>();
			for (int j = 1; j < eventsSplit.length; j++) {
				Map<String, String> map = new HashMap<String, String>();
				for (int i = 0; i < poljaTestTT.length; i++) {
					temp = Tools.provjeraVrati(eventsSplit[j].toString(),
							poljaTestTT[i]);
					if (temp.indexOf("+") != -1) {
						temp = Tools.timeFormatOuter(temp,
								Konstante.vrijemeFormatSoapBack,
								Konstante.prikazVrijeme);
					}
					if (poljaTestTT[i].equals("PRICE")) {
						ukupno = ukupno + Double.parseDouble(temp);
						temp = temp + ".00 HRK";
					}
					if (poljaTestTT[i].equals("RESPONSE_CODE")) {
						if (temp.equals("00")) {
							temp1 = temp;
							temp = "FLAG00";
							uspjeleTrans++;
						} else {
							temp1 = temp;
							temp = "FLAGXX";
							String minus = Tools.lameParser3(
									eventsSplit[j].toString(),
									"<" + "PRICE" + ">", "</" + "PRICE" + ">",
									0, 0).toString();
							ukupno = ukupno - Double.parseDouble(minus);
						}
					}
					if (poljaTestTT[i].equals("TYPE")) {
						temp1 = temp;
						boolean flag = true;
						for (int k = 0; k < standardRisk.length; k++) {
							if (temp.equals(standardRisk[k])) {
								standardEvents++;
								temp = "FLAG00";
								flag = false;
							}
						}
						for (int k = 0; k < highRisk.length; k++) {
							if (temp.equals(highRisk[k])) {
								highRiskEvents++;
								temp = "FLAGXX";
								flag = false;
							}
						}
						if (flag) {
							temp = "FLAGYY";
						}
					}
					Login.testTemp[j - 1][i] = temp;
					map.put(poljaTestTT[i], temp);
					if (temp1 != null) {
						Login.testTemp[j - 1][i + 1] = temp1;
						map.put("FLAG", temp1);
					} else {
						
					}
				}
				ukupnoTrans = eventsSplit.length - 1;
				postotakTrans = (float) uspjeleTrans / (float) (ukupnoTrans);

				if (eventsSplit[j].toString().indexOf(">Err<") != -1) {
					Login.testTemp[j - 1][poljaTestTT.length + 1] = "PrintErr";
					map.put("PrinterStat", "PrintErr");
				} else if (eventsSplit[j].toString().indexOf(">OK<") != -1) {
					Login.testTemp[j - 1][poljaTestTT.length + 1] = "PrintOK";
					map.put("PrinterStat", "PrintOK");
				} else {
					Login.testTemp[j - 1][poljaTestTT.length + 1] = "Printn/a";
					map.put("PrinterStat", "Printn/a");
				}
				listOfMaps.add(map);
			}
		}
	}

	private void eventsExe() {
		Button b1 = (Button) findViewById(R.id.eventsButton);
		b1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				inicijalizirajVrijeme();
				grad = spinner1.getSelectedItem().toString();
				standardEvents = 0;
				mediumRiskEvents = 0;
				highRiskEvents = 0;
				uspjeleTrans = 0;
				ukupnoTrans = 0;
				postotakTrans = 0f;
				if (Tools.isOnline(Procesing.this) == false) {
					Tools.internetDialog(context, Procesing.this);
				} else if (serverStatus == false) {
					Toast.makeText(getApplicationContext(),
							"Problemi sa serverom!!", Toast.LENGTH_SHORT)
							.show();
				} else {
					Login.eventFlag = Login.EventType.events;
					new soapTaskEventsTest().execute(poljaTestEvents);
				}
			}
		});
	}

	/** Inicijalizacija spinnera */
	private void napraviSpinerGrad() {
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, Login.listGradova);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(dataAdapter);
	}
}