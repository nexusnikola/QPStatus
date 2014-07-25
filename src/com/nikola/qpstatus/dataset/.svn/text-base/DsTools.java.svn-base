package com.nikola.qpstatus.dataset;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

public class DsTools {

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
		String procisceni = lameParser(set, "Result", "/soap:Body", 0, 7);
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
	
	/** Funkcija vadi vrijednosti iz sekvence pomocu kljuceva */
	
	public static String[] vadiVrijednost(String sekvenca, String[] poljaTestTT) {
		String[] poljeVal = new String[poljaTestTT.length];
        for (int i = 0; i < poljaTestTT.length; i++) {
        	poljeVal[i] = lameParser3(sekvenca, "<"+poljaTestTT[i]+">", "</"+poljaTestTT[i]+">", 0, 0).toString();
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
			return lameParser3(sekvenca, "<"+polje+">", "</"+polje+">", 0, 0).toString();
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
}
