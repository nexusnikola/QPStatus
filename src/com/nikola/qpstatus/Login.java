package com.nikola.qpstatus;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;

@SuppressLint("DefaultLocale")
public class Login {

	public static String grad;
	public static String gradUser;

	public static String prezime;
	public static String ime;
	public static Boolean loggedIn = false;
	public static Boolean admin = false;
	public static Boolean superAdmin = false;

	public static String naziv;
	public static String adresa;
	public static String mjesto;
	public static String oib;
	public static String iznosNagodbe;
	public static String sifraDrzave;
	public static String nalozi;
	public static String racuni;
	public static String predracuni;
	public static String opomene;
	public static String pdv;
	public static int azuriranje = 0;

	public static int selectedPositionSpinner = 0;
	public static List<String> listGradova = new ArrayList<String>();

	public static String[][] testTemp = null;
	public static EventType eventFlag = EventType.nothing;

	public static Map<String, String> userPass;

	/** OPTIONS */
	public static float postoTransUp;
	public static float postoTransDown;
	public static float postoMediumEventsUp;
	public static float postoMediumEventsDown;
	public static float brojHighEvents;
	public static String verzijaApp;

	/** OPTIONS */

	// public static String[] eventsSplit;

	public static enum EventType {
		trans, events, nothing;
	}

	public static void logiranje(String sekvenca) {
		ime = Tools.provjeraVrati(sekvenca, "Ime");
		prezime = Tools.provjeraVrati(sekvenca, "Prezime");
		loggedIn = Tools.provjeraBool(sekvenca, "LoggedOn");
		admin = Tools.provjeraBool(sekvenca, "Admin");
		superAdmin = false;
	}

	public static void logiranjePre(String user, String pass) {
		try {
			URL url = new URL(Konstante.URL_USERS_APP);

			user = user.trim().toUpperCase();
			pass = pass.trim().toUpperCase();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String temp;
			userPass = new HashMap<String, String>();
			listGradova.clear();
			while ((temp = in.readLine()) != null) {
				if (temp.indexOf("<-") == -1 && temp.indexOf("=") != -1) {
					temp = temp.trim().toUpperCase();
					userPass.put(temp.split("=")[0], temp.split("=")[1]);
					listGradova.add(temp.split("=")[0]);
				}
			}
			in.close();
			if (user.equals("admin".toUpperCase())
					&& pass.equals("1a2s3d4f".toUpperCase())) {
				ime = "Super";
				prezime = "Administrator";
				loggedIn = true;
				superAdmin = true;
			} else if (userPass.get(user).equals(pass)) {
				Object test = SoapService.login(0, "", Login.grad, "", user,
						pass);
				logiranje(test.toString());
				loggedIn = true;
				superAdmin = false;
				listGradova.clear();
				listGradova.add(user);
			} else {
				loggedIn = false;
				superAdmin = false;
			}
		} catch (Exception e) {
			loggedIn = false;
			superAdmin = false;
			e.printStackTrace();
		}
	}

	public static void maticniPodaci(String sekvenca) {

		naziv = Tools.provjeraVrati(sekvenca, "Naziv");
		adresa = Tools.provjeraVrati(sekvenca, "Adresa");
		mjesto = Tools.provjeraVrati(sekvenca, "Mjesto");
		oib = Tools.provjeraVrati(sekvenca, "OIB");
		iznosNagodbe = Tools.provjeraVrati(sekvenca, "IznosNagodbe");
		sifraDrzave = Tools.provjeraVrati(sekvenca, "SifraDrzave");
		racuni = Tools.provjeraVrati(sekvenca, "Racuni");
		predracuni = Tools.provjeraVrati(sekvenca, "PredRacuni");
		opomene = Tools.provjeraVrati(sekvenca, "Opomene");
		predracuni = Tools.provjeraVrati(sekvenca, "PredRacuni");
		pdv = Tools.provjeraVrati(sekvenca, "PDV");

	}

	public static String vratiMaticne() {
		return naziv + "\n" + adresa + "\n" + mjesto + "\nOIB: " + oib;
	}

	public static String vratiUserLogin() {
		return ime + " " + prezime;
	}

}
