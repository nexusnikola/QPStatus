package com.nikola.qpstatus;

import java.security.KeyStore;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class SoapService {
	
	public static Object helloWorld(){
        SoapObject _client = new SoapObject(Konstante.NAMESPACE, Konstante.METHOD_NAME);
        SoapSerializationEnvelope _envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        _envelope.setOutputSoapObject(_client);
        HttpTransportSE _ht = new HttpTransportSE(Konstante.URL);
        try {
			_ht.call(Konstante.SOAP_ACTION, _envelope);
			return _envelope.getResponse();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
    }
	
	public static Object getEvent2(int id, String name, String username, String password, String since, String to, 
			String reg, String nalog, String status, String value ) {
		
		try {
			String bodyOut = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
			bodyOut += "<soap:Body><"+Konstante.EVENS_METHOD_NAME+" xmlns=\"" + Konstante.NAMESPACE + "\">";
			bodyOut += "<User><ID>" + id + "</ID><Name>" + name + "</Name><UserName>";
			bodyOut += username + "</UserName><Password>" + password + "</Password></User>";
			bodyOut += "<Filter><TimeSpan><Since>" + since + "</Since><To>" + to + "</To></TimeSpan>";
			bodyOut += "<Reg>" + reg + "</Reg><Nalog>" + nalog + "</Nalog><Status>" + status + "</Status><Value>" + value + "</Value></Filter>";
			bodyOut += "</"+Konstante.EVENS_METHOD_NAME+"></soap:Body></soap:Envelope>";
			
			return soapRequest(bodyOut, Konstante.EVENS_SOAP_ACTION);
		} catch (Exception e) {
			return null;
		}
		
		
	}
	
	public static Object getTransaction(int id, String name, String username, String password, String since, String to, 
			String reg, String nalog, String status, String value ) {
					
			String bodyOut = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
			bodyOut += "<soap:Body><"+Konstante.TRANS_METHOD_NAME+" xmlns=\"" + Konstante.NAMESPACE + "\">";
			bodyOut += "<User><ID>" + id + "</ID><Name>" + name + "</Name><UserName>";
			bodyOut += username + "</UserName><Password>" + password + "</Password></User>";
			bodyOut += "<Filter><TimeSpan><Since>" + since + "</Since><To>" + to + "</To></TimeSpan>";
			bodyOut += "<Reg>" + reg + "</Reg><Nalog>" + nalog + "</Nalog><Status>" + status + "</Status><Value>" + value + "</Value></Filter>";
			bodyOut += "</"+Konstante.TRANS_METHOD_NAME+"></soap:Body></soap:Envelope>";

			return soapRequest(bodyOut, Konstante.TRANS_SOAP_ACTION);
	}
	
	private static String vratiXml(int id, String name, String username, String password, String methodName){
		
		String bodyOut = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		bodyOut += "<soap:Body><"+methodName+" xmlns=\"" + Konstante.NAMESPACE + "\">";
		bodyOut += "<User><ID>" + id + "</ID><Name>" + name + "</Name><UserName>";
		bodyOut += username + "</UserName><Password>" + password + "</Password></User>";
		bodyOut += "</"+methodName+"></soap:Body></soap:Envelope>";
		
		return bodyOut;
	}
	
	public static Object getPodaci(int id, String name, String username, String password, String methodName, String soapAction ) {
				
		String xml = vratiXml(id, username, username, password, methodName);
		return soapRequest(xml, soapAction);
	}
	
	public static Object getMaticni(int id, String name, String username, String password ) {
		
		String bodyOut = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		bodyOut += "<soap:Body><"+Konstante.MATICNI_METHOD_NAME+" xmlns=\"" + Konstante.NAMESPACE + "\">";
		bodyOut += "<User><ID>" + id + "</ID><Name>" + name + "</Name><UserName>";
		bodyOut += username + "</UserName><Password>" + password + "</Password></User>";
		bodyOut += "</"+Konstante.MATICNI_METHOD_NAME+"></soap:Body></soap:Envelope>";
		
		return soapRequest(bodyOut, Konstante.MATICNE_SOAP_ACTION);

	}
	
	public static Object login(int id, String name, String username, String password, String lUsername, String lPassword  ) {
				
		String bodyOut = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		bodyOut += "<soap:Body><"+Konstante.LOGIN_METHOD_NAME+" xmlns=\"" + Konstante.NAMESPACE + "\">";
		bodyOut += "<User><ID>" + id + "</ID><Name>" + name + "</Name><UserName>";
		bodyOut += username + "</UserName><Password>" + password + "</Password></User><UserName>" + lUsername +"</UserName><Password>"+ lPassword +"</Password>";
		bodyOut += "</"+Konstante.LOGIN_METHOD_NAME+"></soap:Body></soap:Envelope>";
		
		return soapRequest(bodyOut, Konstante.LOGIN_SOAP_ACTION);
	}
	
	public static Object soapRequest(String xml, String soapAction) {

		String response= null;

		HttpPost httpPost = new HttpPost(Konstante.URL);
	
		HttpClient httpClient = getNewHttpClient();
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.encodingStyle = SoapSerializationEnvelope.ENC;
		
		try {		
		
			StringEntity se = new StringEntity(xml, HTTP.UTF_8);

			se.setContentType("text/xml; charset=UTF-8");
			httpPost.addHeader(soapAction, Konstante.URL);
			httpPost.setEntity(se);
	
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity resEntity = httpResponse.getEntity();
			response = EntityUtils.toString(resEntity);
			
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static HttpClient getNewHttpClient() {
	    try {
	        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	        trustStore.load(null, null);
	
	        SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
	        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	
	        HttpParams params = new BasicHttpParams();
	        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
	
	        SchemeRegistry registry = new SchemeRegistry();
	        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	        registry.register(new Scheme("https", sf, 443));
	
	        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
	
	        return new DefaultHttpClient(ccm, params);
	    } catch (Exception e) {
	        return new DefaultHttpClient();
	    }
	}
	
}
