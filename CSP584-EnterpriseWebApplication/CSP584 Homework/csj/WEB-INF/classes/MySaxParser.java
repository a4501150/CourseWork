
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

public class MySaxParser extends DefaultHandler {
	
   
    String XmlFileName;
    String elementValueRead;
	
	
	
	static HashMap<Integer,BigSpeaker> bigspeakers;
	static HashMap<Integer,SmallSpeaker> smallspeakers;
	
	static HashMap<Integer,ClassicWatch> classicwatches;
	static HashMap<Integer,SmartWatch> smartwatches;
	
	static HashMap<Integer,EarPhone> earphones;
	static HashMap<Integer,HeadPhone> headphones;
		
	static HashMap<Integer,SmartPhone> smartphones;
	static HashMap<Integer,Laptop> laptops;
	
	static HashMap<Integer,FlashDrive> flashdrives;
	static HashMap<Integer,HardDrive> harddrives;
	static HashMap<Integer,MemoryCard> memorycards; 
	
	BigSpeaker bs;
	SmallSpeaker ss;
	
	ClassicWatch cw;
	SmartWatch sw;
	
	EarPhone ep;
	HeadPhone hp;
	
	SmartPhone sp;
	Laptop	   lp;
	
	FlashDrive fd;
	HardDrive  hd;
	MemoryCard mc;
	
	String currentlyBlock;
    
    public MySaxParser(String XmlFileName) {
        this.XmlFileName = XmlFileName;
        
		bigspeakers = new HashMap<Integer,BigSpeaker>();
		smallspeakers = new HashMap<Integer, SmallSpeaker>();
		
		classicwatches = new HashMap<Integer, ClassicWatch>();
		smartwatches = new HashMap<Integer, SmartWatch>();
		
		earphones = new HashMap<Integer, EarPhone>();
		headphones = new HashMap<Integer, HeadPhone>();
		
		smartphones = new HashMap<Integer, SmartPhone>();
		laptops = new HashMap<Integer, Laptop>();
		
		flashdrives = new HashMap<Integer, FlashDrive>();
		harddrives = new HashMap<Integer, HardDrive>();
		memorycards = new HashMap<Integer, MemoryCard>();
		
        parseDocument();
        prettyPrint();
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
            System.out.println("IO error");
        }
    }


    private void prettyPrint() {
	
    	
 
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
        	
            switch(cat) {
            
            case "BigSpeaker":
            	bs = new BigSpeaker();
            	bs.setId(id);
            	bs.setCatagory(cat);
            	currentlyBlock= "BigSpeaker";
            	break;
            	
            case "SmallSpeaker":
            	ss = new SmallSpeaker();
            	ss.setId(id);
            	ss.setCatagory(cat);
            	currentlyBlock= "SmallSpeaker";
            	break;
            
            case "ClassicWatch":
            	cw = new ClassicWatch();
            	cw.setId(id);
            	cw.setCatagory(cat);
            	currentlyBlock= "ClassicWatch";
            	break;
            	
            case "SmartWatch":
            	sw = new SmartWatch();
            	sw.setId(id);
            	sw.setCatagory(cat);
            	currentlyBlock= "SmartWatch";
            	break;
            	
            case "EarPhone":
            	ep = new EarPhone();
            	ep.setId(id);
            	ep.setCatagory(cat);
            	currentlyBlock= "EarPhone";
            	break;
            	
            case "HeadPhone":
            	hp = new HeadPhone();
            	hp.setId(id);
            	hp.setCatagory(cat);
            	currentlyBlock= "HeadPhone";
            	break;
            	
            case "Laptop":
            	lp = new Laptop();
            	lp.setId(id);
            	lp.setCatagory(cat);
            	currentlyBlock= "Laptop";
            	break;
            	
            case "SmartPhone":
            	sp = new SmartPhone();
            	sp.setId(id);
            	sp.setCatagory(cat);
            	currentlyBlock= "SmartPhone";
            	break;
            	
            case "HardDrive":
            	hd = new HardDrive();
            	hd.setId(id);
            	hd.setCatagory(cat);
            	currentlyBlock= "HardDrive";
            	break;
            	
            case "FlashDrive":
            	fd = new FlashDrive();
            	fd.setId(id);
            	fd.setCatagory(cat);
            	currentlyBlock= "FlashDrive";
            	break;
            	
            case "MemoryCard":
            	mc = new MemoryCard();
            	mc.setId(id);
            	mc.setCatagory(cat);
            	currentlyBlock= "MemoryCard";
            	break;
            	
            	default:
            		break;
        
            }
        }

    }

    @Override
    public void endElement(String str1, String str2, String element) throws SAXException {
 
        if (element.equals("Product")) {
        	
        	switch(currentlyBlock) {
        	
        	 case "BigSpeaker":
             	
             	bigspeakers.put(bs.getId(),bs);
             	
             	break;
             	
             case "SmallSpeaker":

             	smallspeakers.put(ss.getId(),ss);
             	break;
             
             case "ClassicWatch":

             	classicwatches.put(cw.getId(),cw);
             	break;
             	
             case "SmartWatch":

             	smartwatches.put(sw.getId(),sw);
             	break;
             	
             case "EarPhone":

             	earphones.put(ep.getId(),ep);
             	break;
             	
             case "HeadPhone":

             	headphones.put(hp.getId(),hp);
             	break;
             	
             case "Laptop":

             	laptops.put(lp.getId(),lp);
             	break;
             	
             case "SmartPhone":

             	smartphones.put(sp.getId(),sp);
             	break;
             	
             case "HardDrive":

             	harddrives.put(hd.getId(),hd);
             	break;
             	
             case "FlashDrive":

             	flashdrives.put(fd.getId(),fd);
             	break;
             	
             case "MemoryCard":

             	memorycards.put(mc.getId(),mc);
             	break;
             	
             	default:
             		break;
        	}
        	
        	return;
        }
        
        
        if (element.equalsIgnoreCase("image")) {
        	sw.setImage(elementValueRead);
	    return;
        }
        if (element.equalsIgnoreCase("name")) {
        	sw.setName(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("accessory")){
        	sw.addAccessory(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("price")){
        	sw.setPrice(Double.parseDouble(elementValueRead));
	    return;
        }
        if(element.equalsIgnoreCase("manufacturer")){
        	sw.setManufacturer(elementValueRead);
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
        new MySaxParser("ProductCatalog.xml");

    }

}
