/*********


http://www.saxproject.org/

SAX is the Simple API for XML, originally a Java-only API. 
SAX was the first widely adopted API for XML in Java, and is a “de facto?standard. 
The current version is SAX 2.0.1, and there are versions for several programming language environments other than Java. 



The following URL from Oracle is the JAVA documentation for the API

https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html


*********/


import java.io.IOException;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;
import Models.*;

////////////////////////////////////////////////////////////

/**************

SAX parser use callback function  to notify client object of the XML document structure. 
You should extend DefaultHandler and override the method when parsin the XML document

***************/

////////////////////////////////////////////////////////////

public class MySaxParserEx extends DefaultHandler {
	String XmlFileName;
    String elementValueRead;
   
    static HashMap<Integer,Product> products;
    
    Product product;
    
    
    public MySaxParserEx(String XmlFileName) {
        this.XmlFileName = XmlFileName;
        
        products = new HashMap<Integer, Product>();

        parseDocument();
        prettyPrint();
    }
    
    public static String difference(String str1, String str2) {
        if (str1 == null) {
            return str2;
        }
        if (str2 == null) {
            return str1;
        }
        int at = indexOfDifference(str1, str2);
        if (at == -1) {
            return "";
        }
        return str2.substring(at);
    }
    
    public static int indexOfDifference(String str1, String str2) {
        if (str1 == str2) {
            return -1;
        }
        if (str1 == null || str2 == null) {
            return 0;
        }
        int i;
        for (i = 0; i < str1.length() && i < str2.length(); ++i) {
            if (str1.charAt(i) != str2.charAt(i)) {
                break;
            }
        }
        if (i < str2.length() || i < str1.length()) {
            return i;
        }
        return -1;
    }


    private void parseDocument() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(XmlFileName, this); 
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
        	System.out.println(XmlFileName);
            System.out.println("IO error");
        }
    }


    private void prettyPrint() {
	
    	System.out.println("Loaded + " + products.size());
    
    }





////////////////////////////////////////////////////////////

/*************

There are a number of methods to override in SAX handler  when parsing your XML document :

    Group 1. startDocument() and endDocument() :  Methods that are called at the start and end of an XML document. 
    Group 2. startElement() and endElement() :  Methods that are called  at the start and end of a document element.  
    Group 3. characters() : Method that is called with the text content in between the start and end tags of an XML document element.


There are few other methods that you could use for notification for different purposes, check the API at the following URL:

https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html

***************/

////////////////////////////////////////////////////////////

    @Override
    public void startElement(String str1, String str2, String elementName, Attributes attributes) throws SAXException {

        if (elementName.equalsIgnoreCase("Product")) {
        	
        	
        	String cat = attributes.getValue("Cat");
        	int id = Integer.parseInt(attributes.getValue("id"));
        	
        	product = new Product();
        	product.setId(id);
        	product.setCatagory(cat);
            
        }

    }

    @Override
    public void endElement(String str1, String str2, String element) throws SAXException {
 
        if (element.equals("Product")) {
        	products.put(product.getId(),product);
	    return;
        }
        if (element.equalsIgnoreCase("image")) {
        	product.setImage(elementValueRead);
	    return;
        }
        if (element.equalsIgnoreCase("name")) {
        	product.setName(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("accessory")){
        	product.addAccessory(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("price")){
        	product.setPrice(Double.parseDouble(elementValueRead));
	    return;
        }
        if(element.equalsIgnoreCase("manufacturer")){
        	product.setManufacturer(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("condition")){
        	product.setCondition(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("discount")){
        	product.setDiscount(Double.parseDouble(elementValueRead));
	    return;
        }
        
        

    }

    @Override
    public void characters(char[] content, int begin, int end) throws SAXException {
        elementValueRead = new String(content, begin, end);
    }


    /////////////////////////////////////////
    // 	     Kick-Start SAX in main       //
    ////////////////////////////////////////

    public static void main(String[] args) {
        new MySaxParserEx("ProductCatalog.xml");

    }

}

