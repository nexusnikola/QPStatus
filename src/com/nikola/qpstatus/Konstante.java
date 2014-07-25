package com.nikola.qpstatus;

public class Konstante {
	
	/** Ime aplikacije */
	public static final String NAME_APP = "QPStatus.apk";	
	public static final String URL_APP = "http://webservice.ipc.hr/Updates/parking/QPStatus_android/QPStatus.apk";
	public static final String URLV_APP = "http://webservice.ipc.hr/Updates/parking/QPStatus_android/version.txt";
	public static final String URL_USERS_APP = "http://webservice.ipc.hr/Updates/parking/QPStatus_android/users.txt";
	public static final String URLH_APP = "http://webservice.ipc.hr/Updates/parking/Android_Parking/History.txt";
	
	/** ParkControl konstante **/	
	
	public static final String SOAP_ACTION = "http://www.ipc.hr/HelloWorld";
	public static final String METHOD_NAME = "HelloWorld";
	public static final String EVENS_METHOD_NAME = "GetQuickParkEvents";
	public static final String EVENS_SOAP_ACTION = "http://www.ipc.hr/GetQuickParkEvents";
	public static final String TRANS_METHOD_NAME = "GetQuickParkTrans";
	public static final String TRANS_SOAP_ACTION = "http://www.ipc.hr/GetQuickParkTrans";
	public static final String MATICNI_METHOD_NAME = "MaticniPodaci";
	public static final String MATICNE_SOAP_ACTION = "http://www.ipc.hr/MaticniPodaci";
	public static final String LOGIN_METHOD_NAME = "Login";
	public static final String LOGIN_SOAP_ACTION = "http://www.ipc.hr/Login";
	public static final String LIST_METHOD_NAME = "GetQuickParkList";
	public static final String LIST_SOAP_ACTION = "http://www.ipc.hr/GetQuickParkList";
	
	/* ParkControl konstante, zajednicke */
	
	public static final String NAMESPACE = "http://www.ipc.hr/";
	public static final String URL = "http://plac.ipc.hr/service.asmx?wsdl";
	
	/** Vremenske konstante */
	
	public static final String vrijemeFormatSoap = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZ";
	public static final String vrijemeFormatSoapBack = "yyyy-MM-dd'T'HH:mm:ssZ";
	public static final String unosVrijeme = "dd.MM.yyyy HH:mm:ss";
	public static final String unosVrijemeAlt = "d.MM.yyyy";
	public static final String prikazVrijeme = "dd.MM.yyyy HH:mm";
	
	/** postavke */
	public static final String PREFS_NAME = "qpPrefFile";
	public static final String PREF_USERNAME = "username";
	public static final String PREF_GRAD = "grad";
	public static final String PREF_PASSWORD = "password";
	public static final String PREF_ZAPAMTI = "zapamti";
	
	/** OPTIONS */ 
	public static final String PREFS_NAME_OPTIONS = "options";
	public static final String PREFS_TRANS_UP = "trasUp";
	public static final String PREFS_TRANS_DOWN = "trasDown";
	public static final String PREFS_MEDIUM_UP = "mediumUP";
	public static final String PREFS_MEDIUM_DOWN = "mediumDown";
	public static final String PREFS_HIGH = "high";
	/** OPTIONS */
}
