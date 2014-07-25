package com.nikola.qpstatus.dataset;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.widget.ArrayAdapter;

public class DataSet {

	private String ulaz;
	private ByteArrayInputStream is;
	private Document dom;
	private Element docEle;
	private NodeList nl;
	private String rawData;
	//private Element el;
	
	public String getRawData() {
		return rawData;
	}

	public String getUlaz() {
		return ulaz;
	}

	public void setUlaz(String ulaz) {
		this.ulaz = DsTools.procistiSetXML(ulaz);
	}
	
	public DataSet(String ulaz, String tag) throws SAXException, IOException, ParserConfigurationException {
		super();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		is = new ByteArrayInputStream(ulaz.getBytes());
		dom = db.parse(is);
		docEle = dom.getDocumentElement();
		nl = docEle.getElementsByTagName(tag);
		//el = (Element) nl.item(0);
	}
	
	/** Vraca broj elemenata */
	public int getCount(){
		return  nl.getLength();
	}
	
	/** Vraca element preko pozicije */
	public Element getElementById(int id){
		Element el = (Element) nl.item(id);
		return el;
	}
	
	/** Vraca nodelistu preko pozicije */
//	public Node getNode(String tagName, NodeList nodes) {
//		Node node = nodes.item(x);
//		if (node.getNodeName().equalsIgnoreCase(tagName)) {
//			return node;
//	    }	 
//	    return null;
//	}
	
	/** Vraca string elementa preko pozicije i tag */
	public String getElementsField(int id, String tag){
		Element el = getElementById(id);
		return getTextValue(el, tag);		
	}
	
	/** Vraca boolean elementa preko pozicije i tag */
	public boolean getBooleanElementsField(int id, String tag){
		Element el = getElementById(id);
		return Boolean.parseBoolean(getTextValue(el, tag));		
	}
	
	/** Vraca string elementa preko pozicije i tag */
	public String getElementsField(int id, String tag, String tag2){
		return tag2;
		/**Element firstNode = (Element) nl.item(id); 
		NodeList nl2 = firstNode.getElementsByTagName(tag); 
		return getTextValue((Element) node.getFirstChild(), tag);
		
		for (int i = 0; i < dataChild.getLength(); i++) {
			node = nodes.item(x);
			if(node.getNodeName().equalsIgnoreCase(tag)){
				return
			}
		}
		
		NodeList nlLocal = el.getElementsByTagName(tag);
		el = (Element) nlLocal.item(id2);
		//return getTextValue(el, tag2);	
		return el.getAttributeNode("Upper");*/
	}
	
	protected Node getNode(String tagName, NodeList nodes) {
	    for ( int x = 0; x < nodes.getLength(); x++ ) {
	        Node node = nodes.item(x);
	        if (node.getNodeName().equalsIgnoreCase(tagName)) {
	            return node;
	        }
	    }
	    return null;
	}
	
	protected String getNodeValue(String tagName, NodeList nodes ) {
	    for ( int x = 0; x < nodes.getLength(); x++ ) {
	        Node node = nodes.item(x);
	        if (node.getNodeName().equalsIgnoreCase(tagName)) {
	            NodeList childNodes = node.getChildNodes();
	            for (int y = 0; y < childNodes.getLength(); y++ ) {
	                Node data = childNodes.item(y);
	                if ( data.getNodeType() == Node.TEXT_NODE )
	                    return data.getNodeValue();
	            }
	        }
	    }
	    return "";
	}
	
	/** Vraca integer elementa preko pozicije i tag */
	public int getElementsFieldInt(int id, String tag) throws Exception {
		Element el = getElementById(id);
		return getIntValue(el, tag);	
	}
	
	/** Popuni spinner prema nazivu polja 
	 * @param context */
	public ArrayAdapter<String> spinnerData(String tag, Context context){
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, 
				android.R.layout.simple_spinner_item, vratiListuPolja(tag));
		 dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return dataAdapter;
	}
	
	/** Vraca listu svih polja određenih tagom */
	public List<String> vratiListuPolja(String tag){
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < getCount(); i++) {
			list.add(getElementsField(i, tag));
		}
		return list;
	}
	
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}
		return textVal;
	}

	private int getIntValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Integer.parseInt(getTextValue(ele,tagName));
	}

}
