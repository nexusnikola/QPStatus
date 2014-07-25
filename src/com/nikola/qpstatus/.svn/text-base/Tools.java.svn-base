package com.nikola.qpstatus;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class Tools {

	/** Konstante za parsanje */
	
	public static final String ODVOJI_SETOVE = "\\}; MyDS=anyType\\{";
	
	/** Klase za manipulaciju sa internet konekcijama */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setMobileDataEnabled(Context context, boolean enabled)throws Exception {
		   final ConnectivityManager conman = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
		   final Class conmanClass = Class.forName(conman.getClass().getName());
		   final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
		   iConnectivityManagerField.setAccessible(true);
		   final Object iConnectivityManager = iConnectivityManagerField.get(conman);
		   final Class iConnectivityManagerClass =  Class.forName(iConnectivityManager.getClass().getName());
		   final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
		   setMobileDataEnabledMethod.setAccessible(true);

		   setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
		}
	
	public static void setWifiState(Context context, boolean enabled){
		WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		wifiManager.setWifiEnabled(enabled);
	}
	
	/** Formatiranje vremena u yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZ formatu */ 
	
	public static String timeFormatInter(String vrijemeInput, String formatIz, String formatU){
		String vrijeme = null;
		String vrijemeInput1 = vrijemeInput + " 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat(formatU);
        Date dateObj = new Date();
        SimpleDateFormat curFormater = new SimpleDateFormat(formatIz); 
        try {
			dateObj = curFormater.parse(vrijemeInput1);
		} catch (ParseException e1) {
		}
        vrijeme = sdf.format(dateObj);
        String izlaz = vrijeme.substring(0, vrijeme.length()-2);
		return izlaz + ":00";
	}
	
	public static String timeFormatOuter(String vrijemeInput, String formatIz, String formatU){
		String vrijeme = null;
        vrijeme = vrijemeInput.substring(0, vrijemeInput.length()-3);
        vrijeme = vrijeme + "00";
		SimpleDateFormat sdf = new SimpleDateFormat(formatU);
        Date dateObj = new Date();
        SimpleDateFormat curFormater = new SimpleDateFormat(formatIz); 
        try {
			dateObj = curFormater.parse(vrijeme);
		} catch (ParseException e1) {
		}
        vrijeme = sdf.format(dateObj);

		return vrijeme;
	}
	
	public static String lameParser(String input, String start, String kraj, int offsetStart, int offsetKraj){

	    String string = input.substring(input.indexOf(start)+offsetStart, input.indexOf(kraj, input.indexOf(start) + offsetKraj));
	    
	    return string;
	 }	
	
	public static String lameParser2(String input, String ime, String kraj){

	    String string = input.substring(input.indexOf(ime), input.indexOf(kraj)+kraj.length()+2);
	    
	    return string;
	 }
	
	public static String lameParser3(String input, String start, String kraj, int offsetStart, int offsetKraj){

	    String string = input.substring(input.indexOf(start)+offsetStart+start.length(), input.indexOf(kraj, input.indexOf(start) + offsetKraj));
	    
	    return string;
	 }
	
	/** Parser koji zavrsava na kraju ulaznoga seta sa mogucnosti podesavanja pomocu offseta */
	
	public static String lameParserKraj(String input, String start,int ofsetStart, int ofsetKraj){

	    String string = input.substring(input.indexOf(start)+ofsetStart, input.length()-ofsetKraj);
	    
	    return string;
	 }
	
	/** Vraca asocijativno polje
	 * Ulazni parametri su zapisani u polju i to u slijedecvem obliku "kljuc=vrijednost" */
	
	public static Hashtable<String, String> toHashTable(String[] polje){
		
		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		
		for(String s: polje){
	    	String[] array = s.split("=");
	    	String sKey ="", sValue="";
	    	if(array.length > 1){
	    		sKey = array[0].trim(); 
	    		sValue = array[1].trim();
	    		hashTable.put(sKey, sValue);
	    	}
	    }
		
		return hashTable;
	}
	
	/** Vraca asocijativno polje
	 * Ulazni parametri su zapisani u polju i to u slijedecvem obliku "kljuc=vrijednost;kljuc=vrijednost;kljuc=vrijednost;" 
	 * Ulazni parametar key odreduje koji kljuc ce se uzeti iz stringa i koristiti kao kljuc u polju*/
	
	public static Hashtable<String, String> toHashTableMultiValue(String[] polje, String key){
			
			Hashtable<String, String> hashTable = new Hashtable<String, String>();
			
			for(String s: polje){
				String sKey ="";
				sKey = valueExtractor(key, s).trim();
				s.trim();
				hashTable.put(sKey, s);
		    }
			
			return hashTable;
		}
	
	/** Ekstraktor koji vadi vrijednosti izme�u znaka poslije naziva vrijednosti i ; */
	
	public static String valueExtractor(String key, String sekvenca){
		String value = lameParser(sekvenca, key, ";", key.length()+1, 0);
		return value;
	}
	
	public static String procistiSetKSOAP(String set){
		String procisceni = lameParserKraj(set, "MyDS=anyType", 13, 8);
		return procisceni;
	}
	
	public static String procistiSetXML(String set){
		String procisceni = lameParser(set, "msdata:rowOrder=\"0\"", "</NewDataSet", 0, 7);
		return procisceni;
	}
	
	public static String procistiPolje(String set){
		String procisceni = lameParserKraj(set, "; anyType", 10, 7);
		return procisceni;
	}
	
	/** Ekstraktor koji vadi kljuceve iz stringa tipa "kljuc=vrijednost;kljuc=vrijednost;kljuc=vrijednost;"
	 * ; je prvi separator, a = je drugi */
	
	public static String[] keyExtractorPolje(String separator1, String separator2, String sekvenca){
		sekvenca.trim();
		String[] value = sekvenca.split(separator1);
		StringBuilder value1 = new StringBuilder();
		for (int j = 0; j < value.length; j++) {
			String[] tempk = value[j].split(separator2);
			value1.append(tempk[0].trim());
			value1.append(";");
		}
		value = value1.toString().split(";");
		return value;
	}
	
	/** Izvlaci kljuceve hashtable i stavlja ih u arraylist, prikladno za spinnere */
	
	public static List<String> hashToList(Hashtable<String, String> hashtable){
		Set<String> keys = hashtable.keySet();
		final List<String> list = new ArrayList<String>();
		Iterator<String> iterator = keys.iterator();
		while(iterator.hasNext()){
			String val1 = iterator.next();
			list.add(val1);
		}
		return list;
	}
	
	/** Funkcija koja provjerava stanje servera */
	
	public static boolean checkServer(){
		try {
			String hello = SoapService.helloWorld().toString();
			if(hello.equals("Hello World")){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/** Funkcija vadi vrijednosti iz sekvence pomocu kljuceva */
	
	public static String[] vadiVrijednost(String sekvenca, String[] poljaTestTT) {
		String[] poljeVal = new String[poljaTestTT.length];
        for (int i = 0; i < poljaTestTT.length; i++) {
        	poljeVal[i] = Tools.lameParser3(sekvenca, "<"+poljaTestTT[i]+">", "</"+poljaTestTT[i]+">", 0, 0).toString();
        }
        return poljeVal;
	}
	
	public static Hashtable<String, String> podaciKljuc(String[] key, String[] value){
		Hashtable<String, String> vrijednosti = new Hashtable<String, String>();
		for (int i = 0; i < key.length; i++) {
			vrijednosti.put(key[i], value[i]);
		}
		return vrijednosti;
	}
	
	/** Provjerava dali postoji polje u sekvenci i vraca vrijednost */
	
	public static String provjeraVrati(String sekvenca, String polje){
		if(sekvenca.toString().indexOf(polje) != -1){
			return Tools.lameParser3(sekvenca, "<"+polje+">", "</"+polje+">", 0, 0).toString();
		} else {
			return "n/a";
		}
	}
	
	public static boolean provjeraBool(String sekvenca, String polje){
		String temp = provjeraVrati(sekvenca, polje);
		if(temp.equals("true")){
			return true;
		} else {
			return false;
		}
	}
	
	/** SHA1 generator */
	
	private static String convertToHex(byte[] data) { 
	    StringBuffer buf = new StringBuffer();
	    int length = data.length;
	    for(int i = 0; i < length; ++i) { 
	        int halfbyte = (data[i] >>> 4) & 0x0F;
	        int two_halfs = 0;
	        do { 
	            if((0 <= halfbyte) && (halfbyte <= 9)) 
	                buf.append((char) ('0' + halfbyte));
	            else 
	                buf.append((char) ('a' + (halfbyte - 10)));
	            halfbyte = data[i] & 0x0F;
	        }
	        while(++two_halfs < 1);
	    } 
	    return buf.toString();
	}

	public static String SHA1(String text) { 
	    try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] sha1hash = new byte[40];
			md.update(text.getBytes("iso-8859-1"), 0, text.length());
			sha1hash = md.digest();
			return convertToHex(sha1hash);
		} catch (NoSuchAlgorithmException e) {
			return null;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	} 
	
	/** Popuni spinner */
	
	public static ArrayList<String> popuniSpinner(String[] polja){
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < polja.length; i++) {
			list.add(polja[i]);
		}
		return list;
	}
	
	/** Vrati poziciju iz polja stringova */
	public static int vratiPoziciju(String[] array, String value) {
		int temp = 0;
	    for(int i=0; i<array.length; i++){
	    	if(array[i].toString().equals(value)){
	    		temp = i;
	    	}
	    } 
	    return temp;
	}
	
	/** Funkcija koja provjerava dali je uređaj online */
	public static boolean isOnline(Activity activity) {
	    ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnected()) {
	        return true;
	    } else {
	        return false;
	    }
	}
	
	/** Dijalog koji se pojavljuje kada je ureÄ‘aj ofline i pomoÄ‡u njega se ukljuÄ‡uje internet konekcija 
	 * @param context */
	public static void internetDialog(final Context context,  final Activity activity){

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle("Nema interneta!");
		alertDialogBuilder
			.setMessage("Za ispravno funkcioniranje pograma potrebno je ukljuÄ‡iti internet!")
			.setCancelable(false)
			.setPositiveButton("Internet",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					try {
						Tools.setMobileDataEnabled(context, true);
	      				pricekajOnline(context, activity);
      			} catch (Exception e) {	}
				}
			  })
			.setNeutralButton("WiFi", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					Tools.setWifiState(context, true);
					pricekajOnline(context, activity);
				}
			})
			.setNegativeButton("Izlaz",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					System.exit(0);
				}
			});
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
	}
	  
	  /** Funkcija koja ÄŤeka dok se ureÄ‘aj ne spoji na internet */
	  private static void pricekajOnline(Context context, final Activity activity){
		  final ProgressDialog dialog = new ProgressDialog(context);
		  dialog.setMessage("Molim pricekajte da se ureÄ‘aj spoji na internet...");
		  dialog.show();
		  new Thread(new Runnable() {
		        public void run() {
		        	while(!Tools.isOnline(activity)){
		        		try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {}
		        	}
			        dialog.dismiss();
		        }
		    }).start();
	  }
	  
	  /** Dialog sa jednim gumbom */
		public static void dialogUpozorenjaTest(String poruka, Context context,
				int ikona, final Runnable func) {
			try {
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
										try {
											if (func == null) {

											} else {
												func.run();
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								});

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			} catch (Exception e) {
				e.printStackTrace();
				// log.execute(e.getMessage() + " /Tools line 127");
			}
		}
		
		//vraca true ako je razlika veca od delte
		public static boolean integerDiff(int num1, int num2, int delta){
			if(integerDiff(num1, num2) > delta)
				return true;
			else
				return false;
		}
		
		//vraca razliku između dva broja, apsulutna vrijednost
		public static int integerDiff(int num1, int num2){
			if(num1 < num2)
				return num2 - num1;
			else 
				return num1 - num2;
		}
}
